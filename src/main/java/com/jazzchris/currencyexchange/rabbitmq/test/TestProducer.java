package com.jazzchris.currencyexchange.rabbitmq.test;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProducer {

    @Value("${exchange.topic}")
    private String topicExchange;

    @Value("${exchange.direct}")
    private String directExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/topic-exchange/{routingKey}")
    public String sendToTopicExchange(@PathVariable("routingKey") String key, @RequestParam("message") String message) {
        rabbitTemplate.convertAndSend(topicExchange, key, message);
        return String.format("Message %s sent! topicExchange name: %s, key: %s", message, topicExchange, key);
    }

    @GetMapping("/direct-exchange/{routingKey}")
    public String sendToDirectExchange(@PathVariable("routingKey") String key, @RequestParam("message") String message) {
        rabbitTemplate.convertAndSend(directExchange, key, message);
        return String.format("Message %s sent! topicExchange name: %s, key: %s", message, directExchange, key);
    }
}
