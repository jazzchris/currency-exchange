package com.jazzchris.currencyexchange.rabbitmq.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TestConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TestConsumer.class);

    @RabbitListener(queues = "${queue.A}")
    private void readerQueueA(String text) {
        logger.info("Message Received in queue A: " + text);
    }

    @RabbitListener(queues = "${queue.B}")
    private void readerQueueB(String text) {
        logger.info("Message Received in queue B: " + text);
    }

    @RabbitListener(queues = "${queue.C}")
    private void readerQueueC(String text) {
        logger.info("Message Received in queue C: " + text);
    }

    @RabbitListener(queues = "${queue.D}")
    private void readerQueueD(String text) {
        logger.info("Message Received in queue D: " + text);
    }
}
