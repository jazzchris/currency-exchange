package com.jazzchris.currencyexchange.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jazzchris.currencyexchange.stock.Prices;

public class StocksHolder implements PropertyChangeListener {

	private Quotations stocks;
	private Map<LocalDateTime, Quotations> historyStocks = new LinkedHashMap<>();
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setStocks((Quotations) evt.getNewValue());
		historyStocks.put(stocks.getPublicationDate(), stocks);
	}
	
	public Quotations getStocks() {
		return stocks;
	}
	
	public void setStocks(Quotations stocks) {
		this.stocks = stocks;
	}
	
	public LocalDateTime getCurrentPublicationDate() {
		return stocks.getPublicationDate();
	}
	
	public Prices getCurrentPrices(String curr) {
		return stocks.getAllItems().get(curr);
	}
	
	public Map<LocalDateTime, Quotations> getHistoryStocks() {
		return Map.copyOf(historyStocks);
	}
}
