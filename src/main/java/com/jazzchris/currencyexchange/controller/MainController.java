package com.jazzchris.currencyexchange.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.entity.UsersWallet;
import com.jazzchris.currencyexchange.service.UserService;
import com.jazzchris.currencyexchange.stock.Currency;

@Controller
public class MainController {

	//private static Logger logger = LoggerFactory.getLogger(MainController.class);
	
	private static final String MAIN = "main-ajax";
	
	@Autowired private UserService userService;
	
	@GetMapping("/")
	public String main(Authentication auth, Model theModel) {
		Users user = userService.findByUsername(auth.getName()).get();
		TransactionDetails details = new TransactionDetails();
		theModel.addAttribute("user", user);
		theModel.addAttribute("details", details);
		theModel.addAttribute("curr", Currency.FOREIGN);
		theModel.addAttribute("cash", walletToMap(user.getUsersWallet()));
		return MAIN;
	}
	
//	@GetMapping("/")
	public String mainMenu(Authentication auth, Model model) {
		Users user = userService.findByUsername(auth.getName()).get();
		model.addAttribute("user", user);
		model.addAttribute("curr", Currency.FOREIGN);
		return MAIN;
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
