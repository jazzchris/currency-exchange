package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.Status;
import com.jazzchris.currencyexchange.service.FutureOrderService;
import com.jazzchris.currencyexchange.service.TransactionService;
import com.jazzchris.currencyexchange.stock.Currency;
import com.jazzchris.currencyexchange.stock.Prices;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FutureOrderConsumerImpl implements FutureOrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FutureOrderConsumerImpl.class);

    @Autowired
    private FutureOrderService futureOrderService;

    @Override
    @RabbitListener(queues = "${queue.stocks}")
    public List<FutureOrder> ratingsMessage(Stocks message) {
        List<FutureOrder> orders = futureOrderService.findAllByStatus(Status.AWAIT);
        List<FutureOrder> toAccept = filteredOrders(orders, message);
        logger.info("Total orders: " + orders.size() + " orders to accept: " + toAccept.size());
        return toAccept;
    }

    List<FutureOrder> filteredOrders(List<FutureOrder> futureOrders, Stocks stocks) {
        return futureOrders.stream().filter(o -> isAcceptable(o, stocks.getItems().get(o.getCurrency()))).collect(Collectors.toList());
    }

    static boolean isAcceptable(FutureOrder order, Prices prices) {
        assert(order.getStatus() == Status.AWAIT);
        BigDecimal asked = order.getRate();
        BigDecimal actual = prices.getPrice(order.getTransType());
        int compare = asked.compareTo(actual);
        boolean result = false;
        switch (order.getTransType()) {
            case BUY:
                result = compare <= 0;
                break;
            case SELL:
                result = compare >= 0;
                break;
        }
        return result;
    }
}
