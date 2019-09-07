package com.jazzchris.currencyexchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.dao.CashRepository;
import com.jazzchris.currencyexchange.dao.UserRepository;
import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.http.client.StocksProvider;
import com.jazzchris.currencyexchange.stock.Currency;
import com.jazzchris.currencyexchange.stock.Prices;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CashRepository cashRepository;
	
	@Autowired
	private StocksProvider provider;
	
	/**
	 * method processes when user confirm <b>BUY</b> transaction
	 * @return text result
	 */
	@Override
	@Transactional
	public String proceedPurchase(String username, TransactionDetails details) {
		Prices currentPrices = provider.refreshAndGetStocks().getItems().get(details.getCurrency());
		if (!checkActualityOfPrices(details, currentPrices)) {
			return FailMessage.RATES_NOT_ACTUAL.text;
		}
		Users user = userRepository.findByUsername(username).get();
		Cash officeForeign = cashRepository.findByCurrency(details.getCurrency());
		Cash officeBase = cashRepository.findByCurrency(Currency.PLN);
		Cash purchase = foreignCurrencyAmount(details);
		Cash charge = baseCurrencyAmount(details);
		if (!hasUserEnoughFunds(user, charge)) {
			return FailMessage.USER_NO_FUNDS.text;
		}
		if (!hasOfficeEnoughFunds(officeForeign, purchase)) {
			return FailMessage.OFFICE_NO_FUNDS.text;
		}
		Transfer.transfer(user, officeBase, charge);
		Transfer.transfer(officeForeign, user, purchase);
		userRepository.save(user);
		cashRepository.saveAll(Arrays.asList(officeForeign, officeBase));
		return createInfo(details.getTransactionType(), charge, purchase);
	}

	/**
	 * method processes when user confirm <b>SELL</b> transaction
	 * @return text result
	 */
	@Override
	@Transactional
	public String proceedSale(String username, TransactionDetails details) {
		Prices currentPrices = provider.refreshAndGetStocks().getItems().get(details.getCurrency());
		if (!checkActualityOfPrices(details, currentPrices)) {
			return FailMessage.RATES_NOT_ACTUAL.text;
		}
		Users user = userRepository.findByUsername(username).get();
		Cash officeForeign = cashRepository.findByCurrency(details.getCurrency());
		Cash officeBase = cashRepository.findByCurrency(Currency.PLN);
		Cash sale = foreignCurrencyAmount(details);
		Cash price = baseCurrencyAmount(details);
		if (!hasUserEnoughFunds(user, sale)) {
			return FailMessage.USER_NO_FUNDS.text;
		}
		if (!hasOfficeEnoughFunds(officeBase, price)) {
			return FailMessage.OFFICE_NO_FUNDS.text;
		}
		Transfer.transfer(user, officeForeign, sale);
		Transfer.transfer(officeBase, user, price);
		userRepository.save(user);
		cashRepository.saveAll(Arrays.asList(officeForeign, officeBase));
		return createInfo(details.getTransactionType(), price, sale);
	}
	
	private boolean checkActualityOfPrices(TransactionDetails details, Prices prices) {
		return prices.getPrice(details.getTransactionType()).equals(details.getUnitPrice());
	}
		
	private Cash baseCurrencyAmount(TransactionDetails details) {
		Cash baseCurrencyAmount = new Cash(Currency.PLN,
				details.getUnitPrice().multiply(BigDecimal.valueOf(details.getTransUnits())).setScale(2, RoundingMode.HALF_UP));
		return baseCurrencyAmount;
	}
	
	private Cash foreignCurrencyAmount(TransactionDetails details) {
		Cash foreignCurrencyAmount = new Cash(details.getCurrency(), BigDecimal.valueOf(details.getTransUnits()*details.getCurrency().unit));
		return foreignCurrencyAmount;
	}
	
	private boolean hasUserEnoughFunds(Users user, Cash requested) {
		boolean result = false;
		BigDecimal funds = user.getUsersWallet().getByCurrency(requested.getCurrency());
		if (funds.compareTo(requested.getValue())>-1) {
			result = true;
		}
		return result;
	}
	
	private boolean hasOfficeEnoughFunds(Cash available, Cash requested) {
		boolean result = false;
		BigDecimal funds = available.getValue();
		if (funds.compareTo(requested.getValue())>-1) {
			result = true;
		}
		return result;
	}
	
	/**
	 * creates success info about purchase in the following manner:<br>
	 * <p>Success! You have <code>bought/sold</code> <code>foreign</code> for <code>settlement</code>.</p>
	 * @param type transaction type
	 * @param settlement base currency amount (in this case PLN)
	 * @param foreign subject of transaction
	 * @return success info
	 */	
	private String createInfo(TransactionType type, Cash settlement, Cash foreign) {
		StringBuilder builder = new StringBuilder("Success! You have ");
		switch(type) {
		case BUY:
			builder.append("bought ");
			break;
		case SELL:
			builder.append("sold ");
			break;
		}
		builder.append(foreign.toText() + " for " + settlement.toText());
		return builder.toString();
	}
}

enum FailMessage {
	
	CANNOT_CONNECT("Cannot connect to server"),
	RATES_NOT_ACTUAL("Currency rates are not actual"),
	USER_NO_FUNDS("Not enough funds on your account"),
	OFFICE_NO_FUNDS("Not enough funds on Currency Office account");
	
	public final String text;
	
	private FailMessage(String text) {
		this.text = text;
	}
}