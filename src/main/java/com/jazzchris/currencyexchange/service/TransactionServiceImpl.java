package com.jazzchris.currencyexchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.dao.CashRepository;
import com.jazzchris.currencyexchange.dao.UserRepository;
import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.stock.Currency;
import com.jazzchris.currencyexchange.stock.Prices;

import javax.annotation.PostConstruct;

@Service
public class TransactionServiceImpl implements TransactionService {

	private UserRepository userRepository;
	private CashRepository cashRepository;
	private StockService stockService;
	private final MeterRegistry meterRegistry;
//	private Counter transactionCounter = null;
//	private Counter successCounter = null;
//	private Counter ratesNotActualCounter = null;
//	private Counter userNoFundsCounter = null;
//	private Counter officeNoFundsCounter = null;

	@Autowired
	public TransactionServiceImpl(UserRepository userRepository, CashRepository cashRepository, StockService stockService, MeterRegistry meterRegistry) {
		this.userRepository = userRepository;
		this.cashRepository = cashRepository;
		this.stockService = stockService;
		this.meterRegistry = meterRegistry;
	}

//	@PostConstruct
//	public void init() {
//		transactionCounter = meterRegistry.counter("transaction", "type", "total");
//		successCounter = meterRegistry.counter("transaction", "type", "success");
//		ratesNotActualCounter = meterRegistry.counter("transaction", "type", "rates.not.actual");
//		userNoFundsCounter = meterRegistry.counter("transaction", "type", "user.no.funds");
//		officeNoFundsCounter = meterRegistry.counter("transaction", "type", "office.no.funds");
//	}

	/**
	 * method processes when user confirm <b>BUY</b> transaction
	 * @return text result
	 */
	@Override
	@Transactional
	public String proceedPurchase(String username, TransactionDetails details) {
//		transactionCounter.increment();
		//Prices currentPrices = provider.refreshAndGetStocks().getItems().get(details.getCurrency());
		Prices currentPrices = stockService.getStocks().getItems().get(details.getCurrency());
		if (!checkActualityOfPrices(details, currentPrices)) {
//			ratesNotActualCounter.increment();
			return FailMessage.RATES_NOT_ACTUAL.text;
		}
		Users user = userRepository.findByUsername(username).get();
		Cash officeForeign = cashRepository.findByCurrency(details.getCurrency());
		Cash officeBase = cashRepository.findByCurrency(Currency.PLN);
		Cash purchase = foreignCurrencyAmount(details);
		Cash charge = baseCurrencyAmount(details);
		if (!hasUserEnoughFunds(user, charge)) {
//			userNoFundsCounter.increment();
			return FailMessage.USER_NO_FUNDS.text;
		}
		if (!hasOfficeEnoughFunds(officeForeign, purchase)) {
//			officeNoFundsCounter.increment();
			return FailMessage.OFFICE_NO_FUNDS.text;
		}
		Transfer.transfer(user, officeBase, charge);
		Transfer.transfer(officeForeign, user, purchase);
		userRepository.save(user);
		cashRepository.saveAll(Arrays.asList(officeForeign, officeBase));
//		successCounter.increment();
		return createInfo(details.getTransactionType(), charge, purchase);
	}

	/**
	 * method processes when user confirm <b>SELL</b> transaction
	 * @return text result
	 */
	@Override
	@Transactional
	public String proceedSale(String username, TransactionDetails details) {
//		transactionCounter.increment();
		//Prices currentPrices = provider.refreshAndGetStocks().getItems().get(details.getCurrency());
		Prices currentPrices = stockService.getStocks().getItems().get(details.getCurrency());
		if (!checkActualityOfPrices(details, currentPrices)) {
//			ratesNotActualCounter.increment();
			return FailMessage.RATES_NOT_ACTUAL.text;
		}
		Users user = userRepository.findByUsername(username).get();
		Cash officeForeign = cashRepository.findByCurrency(details.getCurrency());
		Cash officeBase = cashRepository.findByCurrency(Currency.PLN);
		Cash sale = foreignCurrencyAmount(details);
		Cash price = baseCurrencyAmount(details);
		if (!hasUserEnoughFunds(user, sale)) {
//			userNoFundsCounter.increment();
			return FailMessage.USER_NO_FUNDS.text;
		}
		if (!hasOfficeEnoughFunds(officeBase, price)) {
//			officeNoFundsCounter.increment();
			return FailMessage.OFFICE_NO_FUNDS.text;
		}
		Transfer.transfer(user, officeForeign, sale);
		Transfer.transfer(officeBase, user, price);
		userRepository.save(user);
		cashRepository.saveAll(Arrays.asList(officeForeign, officeBase));
//		successCounter.increment();
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

