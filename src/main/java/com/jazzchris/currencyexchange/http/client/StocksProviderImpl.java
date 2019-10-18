package com.jazzchris.currencyexchange.http.client;

import java.io.IOException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.springframework.stereotype.Service;

@Service
public class StocksProviderImpl implements StocksProvider {

	private static final Logger logger = LoggerFactory.getLogger(StocksProviderImpl.class);
	
	@Autowired
	private URL url;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Stocks fetch() {
		Stocks stocks = null;
		try {
			stocks = objectMapper.readValue(url, Stocks.class);
		} catch (IOException e) {
			logger.error("Error occurring during refreshing");
		}
		logger.info("Receiving actual currencies from URL. Actual publication date: " + stocks.getPublicationDate());
		return stocks;
	}
}
