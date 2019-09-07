package com.jazzchris.currencyexchange.core;

import java.time.LocalDateTime;
import java.util.Map;

import com.jazzchris.currencyexchange.stock.Prices;

public interface Quotations {

	LocalDateTime getPublicationDate();
	
	Map<String, Prices> getAllItems();
}
