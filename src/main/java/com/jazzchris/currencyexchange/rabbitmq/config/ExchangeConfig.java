package com.jazzchris.currencyexchange.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    @Value("${exchange.topic}")
    private String topicExchange;

    @Value("${exchange.direct}")
    private String directExchange;

    @Value("${exchange.direct.user}")
    private String directUserExchange;

    @Bean
    public DirectExchange directUserExchange() {
        return new DirectExchange(directUserExchange);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }

}
