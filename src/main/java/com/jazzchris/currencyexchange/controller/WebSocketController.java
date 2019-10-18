package com.jazzchris.currencyexchange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazzchris.currencyexchange.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.jazzchris.currencyexchange.ws.client.CurrencyWebSocketHandler;

@Controller
public class WebSocketController {

	//@Autowired
	//private CurrencyWebSocketHandler handler;
	@Autowired
	private StockService stockServiceCacheImpl;

	@Autowired
	private ObjectMapper objectMapper;
	
	@SubscribeMapping("/topic/price")
	public String currentMessage() throws JsonProcessingException {

		//return handler.getTextMessage().getPayload();
		return objectMapper.writeValueAsString(stockServiceCacheImpl.getStocks());
	}

	//new:

//	@SendTo("/topic/price")
//	public String send() throws JsonProcessingException {
//		return objectMapper.writeValueAsString(stockServiceCacheImpl.getStocks());
//	}
}
