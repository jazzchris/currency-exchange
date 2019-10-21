package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.stock.Prices;

public interface TransactionService {

	String proceedPurchase(String username, TransactionDetails details);
	
	String proceedSale(String username, TransactionDetails details);

    String proceedUnchecked(String name, TransactionDetails details);

    String proceedFuture(FutureOrder futureOrder, Prices prices);
}
