package com.jazzchris.currencyexchange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazzchris.currencyexchange.service.StockService;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.jazzchris.currencyexchange.ws.client.CurrencyWebSocketHandler;

@Controller
public class WebSocketController {

	@Autowired
	private StockService stockServiceCacheImpl;

	@Autowired
	private ObjectMapper objectMapper;
	
	@SubscribeMapping("${client.topic}")
	public String currentMessage() throws JsonProcessingException {
		return stocksToJson();
	}

	private String stocksToJson() throws JsonProcessingException {
		return objectMapper.writeValueAsString(stockServiceCacheImpl.getStocks());
	}
}
