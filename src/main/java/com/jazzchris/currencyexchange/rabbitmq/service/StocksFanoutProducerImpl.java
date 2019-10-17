package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StocksFanoutProducerImpl implements StocksFanoutProducer {

    private static final Logger logger = LoggerFactory.getLogger(StocksFanoutProducer.class);

    @Value("${exchange.direct.user}")
    private String directUserExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendStocksToTransact(Stocks message, String key) {
        rabbitTemplate.convertAndSend(directUserExchange, key, message);
        logger.info("Sent new Stocks to queue: " + message.getPublicationDate());
    }

    @Override
    public String allertUsers(Stocks message, String key) {
        return null;
    }
}
