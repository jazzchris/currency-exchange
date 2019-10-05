package com.jazzchris.currencyexchange.controller;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jazzchris.currencyexchange.dto.TransactionSuccessBody;
import com.jazzchris.currencyexchange.dto.UserDto;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.UsersWallet;
import com.jazzchris.currencyexchange.service.TransactionService;
import com.jazzchris.currencyexchange.service.UserService;
import com.jazzchris.currencyexchange.stock.Currency;

@RestController
public class TransactionRestController {

	//private static Logger logger = LoggerFactory.getLogger(MainControllerRest.class);
	
	@Autowired private UserService userService;
	@Autowired private TransactionService transactionService;
	
	@RequestMapping(value="/ajaxBuy", method=RequestMethod.POST)
	public ResponseEntity<?> ajaxBuy(@RequestBody TransactionDetails details, Authentication auth) {
		String result = null;
		switch(details.getTransactionType()) {
		case BUY:
			result = transactionService.proceedPurchase(auth.getName(), details);
			break;
		case SELL:
			result = transactionService.proceedSale(auth.getName(), details);
			break;
		default:
			throw new IllegalArgumentException("Unexpected transaction type");
		}
		if (result.startsWith("Success")) {
			TransactionSuccessBody response = new TransactionSuccessBody();
			response.setResponse(result);
			UserDto userDto = getUser(auth);
			response.setUserDto(userDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(Map.of("response", result), HttpStatus.BAD_REQUEST);	
	}
	
	@GetMapping("/currencySet")
	public Map<String, Collection<Currency>> values() {
		Map<String, Collection<Currency>> map = new LinkedHashMap<>();
		Collection<Currency> currencies = Currency.FOREIGN.values();
		map.put("foreign", currencies);
		return map;
	}
	
	@GetMapping("/user")
	public UserDto getUser(Authentication auth) {
		return userService.findAndConvert(auth.getName());
	}
	
	@GetMapping("/getWallet")
	public UsersWallet getWallet(Authentication auth) {
		return userService.findByUsername(auth.getName()).get().getUsersWallet();
	}

}
