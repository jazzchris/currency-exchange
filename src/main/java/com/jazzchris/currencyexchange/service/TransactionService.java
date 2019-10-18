package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.entity.TransactionDetails;

public interface TransactionService {

	String proceedPurchase(String username, TransactionDetails details);
	
	String proceedSale(String username, TransactionDetails details);

    String proceedUnchecked(String name, TransactionDetails details);
}
