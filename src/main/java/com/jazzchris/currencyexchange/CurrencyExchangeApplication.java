package com.jazzchris.currencyexchange;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jazzchris.currencyexchange.core.StocksHolder;
import com.jazzchris.currencyexchange.dao.CashRepository;
import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.stock.Currency;
import com.jazzchris.currencyexchange.stock.Stocks;
import com.jazzchris.currencyexchange.ws.client.CurrencyWebSocketHandler;

@SpringBootApplication
public class CurrencyExchangeApplication {

	@Autowired
	private CashRepository cashRepository;
	
	@Autowired
	private CurrencyWebSocketHandler webSocketHandler;
	
	@Value("${currency.http}") 
	private String httpUrl;
	
	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeApplication.class, args);
	}
	
	@Bean
	public Stocks getCurrentStocks() {
		return new Stocks();
	}
	
	@Bean
	public URL url() {
		try {
			return new URL(httpUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
	
	@Bean
	public StocksHolder stocksHolder() {
		return new StocksHolder();
	}
	
	@PostConstruct
	public void setChannelListener() {
		webSocketHandler.addPropertyChangeListener(stocksHolder());
	}

	@PostConstruct
	public void setDatabase() {
		Arrays.asList(Currency.values()).stream()
			.forEach(curr -> cashRepository.save(new Cash(curr, 20_000)));
	}
}
