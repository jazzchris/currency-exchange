package com.jazzchris.currencyexchange.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.entity.UsersWallet;
import com.jazzchris.currencyexchange.service.TransactionService;
import com.jazzchris.currencyexchange.service.UserService;
import com.jazzchris.currencyexchange.stock.Currency;

@Controller
public class MainController {

	private static Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired private UserService userService;
	@Autowired private TransactionService transactionService;;
	
	@PostMapping("/proceedBuy")
	public String buyConfirmed(@Valid @ModelAttribute("details") TransactionDetails details, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication auth) {
		if(bindingResult.hasErrors()) {
			return "main";
		}
		details.setTimeStamp(LocalDateTime.now(Clock.systemUTC()));
		logger.info("Buy request at: " + details.getTimeStamp());
		logger.info("Buying price: " + details.getUnitPrice() + details.getCurrency() + " by: " + auth.getName());
		String result = transactionService.proceedPurchase(auth.getName(), details);
		redirectAttributes.addFlashAttribute("result", result);
		return "redirect:/";
	}
	
	@PostMapping("/proceedSell")
	public String sellConfirmed(@Valid @ModelAttribute("details") TransactionDetails details, BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication auth) {
		if(bindingResult.hasErrors()) {
			return "main";
		}
		details.setTimeStamp(LocalDateTime.now(Clock.systemUTC()));
		logger.info("Sell request at: " + details.getTimeStamp());
		logger.info("Selling price: " + details.getUnitPrice() + details.getCurrency() + " by: " + auth.getName());
		String result = transactionService.proceedSale(auth.getName(), details);
		redirectAttributes.addFlashAttribute("result", result);
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String main(Authentication auth, Model theModel) {
		Users user = userService.findByUsername(auth.getName()).get();
		TransactionDetails details = new TransactionDetails();
		theModel.addAttribute("user", user);
		theModel.addAttribute("details", details);
		theModel.addAttribute("curr", Currency.FOREIGN);
		theModel.addAttribute("cash", walletToMap(user.getUsersWallet()));
		return "main";
	}
	
	/**
	 * converts resources to available units,<br>
	 * e.g. 11.56 USD -> 11 units, 167 CZK -> 1 unit
	 * @param usersWallet user's wallet
	 * @param currency currency
	 * @return units available
	 */
	private static BigDecimal toUnits(UsersWallet usersWallet, Currency currency) {
		return usersWallet.getByCurrency(currency).divide(BigDecimal.valueOf(currency.getUnit()), 0, RoundingMode.DOWN);
	}
	
	/**
	 * converts wallet to map, where key is a currency name, and value is available units
	 * @param usersWallet user's wallet
	 * @return wallet map representation
	 */
	private static Map<String, BigDecimal> walletToMap(UsersWallet usersWallet) {
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
		for (Currency c : Currency.values()) {
			BigDecimal cash = toUnits(usersWallet, c);
			if (cash != null) {
				result.put(c.name(), cash);
			}
			else {
				result.put(c.name(), BigDecimal.ZERO);
			}
		}
		return result;
	}
}
