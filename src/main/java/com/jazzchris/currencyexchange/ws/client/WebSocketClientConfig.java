package com.jazzchris.currencyexchange.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class WebSocketClientConfig {
	
	@Value("${currency.websocket}")
	private String uri;
	
	@Autowired
	@Qualifier("currencyWebSocketHandler")
	private WebSocketHandler handler;

	@Bean
	public WebSocketClient webSocketClient() {
		return new StandardWebSocketClient();
	}

	@Bean
	public WebSocketConnectionManager webSocketConnectionManager() {
		WebSocketConnectionManager manager =
				new WebSocketConnectionManager(webSocketClient(), handler, uri);
		manager.setAutoStartup(true);
		return manager;
	}
}
