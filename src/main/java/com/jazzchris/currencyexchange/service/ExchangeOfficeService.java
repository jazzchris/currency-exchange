package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.stock.Currency;

public interface ExchangeOfficeService {

	Cash findCashByCurrency(Currency currency);
	
	void save(Cash cash);
}
