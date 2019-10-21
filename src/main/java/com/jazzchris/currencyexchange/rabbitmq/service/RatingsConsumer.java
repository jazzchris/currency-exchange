package com.jazzchris.currencyexchange.rabbitmq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazzchris.currencyexchange.controller.WebSocketController;
import com.jazzchris.currencyexchange.http.client.StocksProvider;
import com.jazzchris.currencyexchange.service.StockService;
import com.jazzchris.currencyexchange.stock.Stocks;
import com.jazzchris.currencyexchange.ws.client.MessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class RatingsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RatingsConsumer.class);
    private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StockService stockServiceCacheImpl;

    @Value("${client.topic}")
    private String destination;

    public RatingsConsumer(@Autowired MessageSendingOperations brokerMessagingTemplate) {
        this.messagingTemplate = brokerMessagingTemplate;
    }

    @RabbitListener(queues = "${queue.stocks.beta}")
    public void ratingsMessage(String message) throws JsonProcessingException {
        logger.info("Sending new stocks to customers");
        Stocks stocks = stockServiceCacheImpl.getStocks();
        messagingTemplate.convertAndSend(destination, objectMapper.writeValueAsString(stocks));

    }
}
