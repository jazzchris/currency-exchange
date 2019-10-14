package com.jazzchris.currencyexchange.http.client;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazzchris.currencyexchange.stock.Stocks;

@Component
public class StocksProvider {

	private static Logger logger = LoggerFactory.getLogger(StocksProvider.class);
	
	@Autowired private URL url;
	@Autowired private ObjectMapper objectMapper;
	private Stocks stocks;

	private void refreshStocks() {
		Stocks stocks = null;
		try {
			stocks = objectMapper.readValue(url, Stocks.class);
		} catch (IOException e) {
			logger.error("Error occurring during refreshing");
		}
		logger.info("Receiving actual currencies from URL");
		logger.info("URL actual publication date: " + stocks.getPublicationDate());
		this.stocks = stocks;
	}

	@Cacheable("stocks")
	public Stocks refreshAndGetStocks()  {
		logger.info("---->Going to get new Stocks from http...");
		refreshStocks();
		return stocks;
	}
	
	public Stocks getStocks() {
		return stocks;
	}
}
