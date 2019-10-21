package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.Order;
import com.jazzchris.currencyexchange.entity.Status;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.service.FutureOrderService;
import com.jazzchris.currencyexchange.service.OrderService;
import com.jazzchris.currencyexchange.service.StockService;
import com.jazzchris.currencyexchange.service.TransactionService;
import com.jazzchris.currencyexchange.stock.Prices;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FutureOrderConsumerImpl implements FutureOrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FutureOrderConsumerImpl.class);

    @Autowired
    private FutureOrderService futureOrderService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StockService stockServiceCacheImpl;

    @Override
    @RabbitListener(queues = "${queue.stocks}")
    public void ratingsMessage(String message) {
        logger.info("CONSUMER receives message: " + message);
        Stocks stocks = stockServiceCacheImpl.getStocks();
       // List<FutureOrder> orders = futureOrderService.findAllByStatus(Status.AWAIT);
        List<FutureOrder> orders = futureOrderService.findAllAwaited();
        List<FutureOrder> toAccept = filteredOrders(orders, stocks);
        logger.info("Total orders: " + orders.size() + " orders to accept: " + toAccept.size());
        toAccept.forEach(f -> transact(f, stocks.getItems().get(f.getTransactionDetails().getCurrency())));
    }

    List<FutureOrder> filteredOrders(List<FutureOrder> futureOrders, Stocks stocks) {
        return futureOrders.stream().filter(o -> isAcceptable(o, stocks.getItems().get(o.getTransactionDetails().getCurrency()))).collect(Collectors.toList());
    }

    @Transactional
    public void transact(FutureOrder futureOrder, Prices prices) {
        System.err.println("Old rate: " + futureOrder.getTransactionDetails().getUnitPrice());
        TransactionDetails details = convertWithActualPrices(futureOrder, prices);
        System.err.println("New rate: " + details.getUnitPrice());
        String message = null;
        switch (futureOrder.getTransactionDetails().getTransactionType()) {
            case BUY:
                System.err.println("Transaction buy proceed");
                message = transactionService.proceedPurchase(futureOrder.getUsers().getUsername(), details);
                break;
            case SELL:
                System.err.println("Transaction sell proceed");
                message = transactionService.proceedSale(futureOrder.getUsers().getUsername(), details);
                break;
        }
        System.err.println(message);
        if (message.startsWith("Success")) {
            Order order = new Order();
            order.setTransactionDetails(details);
            order.setUsers(futureOrder.getUsers());
            futureOrder.setOrder(order);
            futureOrderService.save(futureOrder);
        }

    }

    static boolean isAcceptable(FutureOrder futureOrder, Prices prices) {
        TransactionDetails details = Objects.requireNonNull(futureOrder.getTransactionDetails());
        boolean result = false;
        switch (details.getTransactionType()) {
            case BUY:
                result = details.getUnitPrice().compareTo(prices.getFor(TransactionType.BUY)) >= 0;
                break;
            case SELL:
                result = details.getUnitPrice().compareTo(prices.getFor(TransactionType.SELL)) <= 0;
        }
        return result;

        //return order.getTransactionDetails().getUnitPrice().compareTo(prices.getFor(order.getTransactionDetails().getTransactionType())) >= 1 /*0*order.getTransactionDetails().getTransactionType().mod*/;
    }

//    public static TransactionDetails convert(FutureOrder order) {
//        TransactionDetails details = new TransactionDetails();
//        details.setCurrency(order.getCurrency());
//        details.setTransactionType(order.getTransType());
//        details.setUnitPrice(order.getRate());
//        details.setTransUnits(order.getAmount().intValue());
//        return details;
//    }

    public static TransactionDetails convertWithActualPrices(FutureOrder order, Prices prices) {
        TransactionDetails old = order.getTransactionDetails();
        TransactionDetails actual = new TransactionDetails();
        actual.setUnitPrice(prices.getFor(old.getTransactionType()));
        actual.setCurrency(old.getCurrency());
        actual.setTransactionType(old.getTransactionType());
        actual.setTransUnits(old.getTransUnits());
        return actual;
        }

//    public static Order convert(FutureOrder future, Prices prices) {
//        Order order = new Order();
//        order.setAmount(future.getAmount());
//        order.setCurrency(future.getCurrency());
//        order.setRate(prices.getFor(future.getTransType()));
//        order.setType(future.getTransType());
//    }
}
