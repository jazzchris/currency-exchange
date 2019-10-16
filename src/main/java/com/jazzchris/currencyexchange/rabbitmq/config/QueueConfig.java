package com.jazzchris.currencyexchange.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Value("${queue.A}")
    private String queueA;

    @Value("${queue.B}")
    private String queueB;

    @Value("${queue.C}")
    private String queueC;

    @Value("${queue.D}")
    private String queueD;

    @Bean
    public Queue queueA() {
        return new Queue(queueA);
    }

    @Bean
    public Queue queueB() {
        return new Queue(queueB);
    }

    @Bean
    public Queue queueC() {
        return new Queue(queueC);
    }

    @Bean
    public Queue queueD() {
        return new Queue(queueD);
    }
}