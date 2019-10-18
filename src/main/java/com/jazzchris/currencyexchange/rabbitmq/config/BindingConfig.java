package com.jazzchris.currencyexchange.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfig {

    @Value("${routing.topic.rabbitmq.#}")
    private String topicRoutingKey;

    @Value("${routing.direct.a}")
    private String directRoutingA;

    @Value("${routing.direct.b}")
    private String directRoutingB;

    @Value("${routing.stocks}")
    private String stockRouting;

    @Value("${routing.stocks.beta}")
    private String stockRoutingBeta;

    @Bean
    public Binding bindingStockBeta(DirectExchange directStocksExchange, Queue queueStocksBeta) {
        return BindingBuilder.bind(queueStocksBeta).to(directStocksExchange).with(stockRoutingBeta);
    }

    @Bean
    public Binding bindingStock(DirectExchange directStocksExchange, Queue queueStocks) {
        return BindingBuilder.bind(queueStocks).to(directStocksExchange).with(stockRouting);
    }

    @Bean
    public Binding bindingQueueA(TopicExchange topicExchange, Queue queueA) {
        return BindingBuilder.bind(queueA).to(topicExchange).with(topicRoutingKey);
    }

    @Bean
    public Binding bindingQueueB(TopicExchange topicExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(topicExchange).with(topicRoutingKey);
    }

    @Bean
    public Binding bindingQueueC(DirectExchange directExchange, Queue queueC) {
        return BindingBuilder.bind(queueC).to(directExchange).with(directRoutingA);
    }

    @Bean
    public Binding bindingQueueD(DirectExchange directExchange, Queue queueD) {
        return BindingBuilder.bind(queueD).to(directExchange).with(directRoutingB);
    }
}
