package com.jazzchris.currencyexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.jazzchris.currencyexchange.ws.client.CurrencyWebSocketHandler;

@Controller
public class WebSocketController {

	@Autowired
	private CurrencyWebSocketHandler handler;
	
	@SubscribeMapping("/topic/price")
	public String currentMessage() {
		return handler.getTextMessage().getPayload();
	}
}
