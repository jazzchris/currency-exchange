package com.jazzchris.currencyexchange.ws.client;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jazzchris.currencyexchange.stock.Stocks;

//@Component
public class CurrencyWebSocketHandler extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(CurrencyWebSocketHandler.class);

	private MessageDecoder decoder = new MessageDecoder();
	private final MessageSendingOperations<String> messagingTemplate;

	@Value("${client.topic}")
	private String destination;

	private TextMessage currentMessage;
	
	private PropertyChangeSupport support;
	private Stocks stocks;
	

	public CurrencyWebSocketHandler(@Autowired @Qualifier("brokerMessagingTemplate") MessageSendingOperations messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
		support = new PropertyChangeSupport(this);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("WebSocket message received");
		currentMessage = message;
		Stocks stocks = decoder.decode(message.getPayload());
		logger.info("WS actual publication date: " + stocks.getPublicationDate());
		setStocks(stocks);
		messagingTemplate.convertAndSend(destination, currentMessage.getPayload());
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("WebSocket connection established");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.error("WebSocket connection error: " + exception.getMessage());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.warn("WebSocket connection closed: " + closeStatus.getReason());
	}
	
	public TextMessage getTextMessage() {
		return currentMessage;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	public void setStocks(Stocks value) {
		support.firePropertyChange("stocks", this.stocks, value);
		this.stocks = value;
	}
}
