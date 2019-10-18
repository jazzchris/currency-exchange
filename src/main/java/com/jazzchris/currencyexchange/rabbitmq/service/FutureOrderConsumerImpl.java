package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.Status;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.service.FutureOrderService;
import com.jazzchris.currencyexchange.service.StockService;
import com.jazzchris.currencyexchange.service.TransactionService;
import com.jazzchris.currencyexchange.stock.Prices;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FutureOrderConsumerImpl implements FutureOrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FutureOrderConsumerImpl.class);

    @Autowired
    private FutureOrderService futureOrderService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StockService stockServiceCacheImpl;

    @Override
    @RabbitListener(queues = "${queue.stocks}")
    public void ratingsMessage(String message) {
        logger.info("CONSUMER receives message: " + message);
        Stocks stocks = stockServiceCacheImpl.getStocks();
        List<FutureOrder> orders = futureOrderService.findAllByStatus(Status.AWAIT);
        List<FutureOrder> toAccept = filteredOrders(orders, stocks);
        logger.info("Total orders: " + orders.size() + " orders to accept: " + toAccept.size());
        toAccept.forEach(f -> transact(f, stocks.getItems().get(f.getCurrency())));
    }

    List<FutureOrder> filteredOrders(List<FutureOrder> futureOrders, Stocks stocks) {
        return futureOrders.stream().filter(o -> isAcceptable(o, stocks.getItems().get(o.getCurrency()))).collect(Collectors.toList());
    }

    private void transact(FutureOrder order, Prices prices) {
        TransactionDetails details = convertWithActualPrices(order, prices);
        switch (order.getTransType()) {
            case BUY:
                String message = transactionService.proceedPurchase(order.getUsers().getUsername(), details);
                System.err.println(message);
                if (message.startsWith("Success")) {
                    order.setStatus(Status.DONE);
                    order.setRate(details.getUnitPrice());
                    futureOrderService.save(order);
                }
                break;
            case SELL:
                String message2 = transactionService.proceedSale(order.getUsers().getUsername(), details);
                System.err.println(message2);
                if (message2.startsWith("Success")) {
                    order.setStatus(Status.DONE);
                    order.setRate(details.getUnitPrice());
                    futureOrderService.save(order);
                }
                break;
        }
    }

    static boolean isAcceptable(FutureOrder order, Prices prices) {
        assert(order.getStatus() == Status.AWAIT);
        return order.getRate().compareTo(prices.getFor(order.getTransType())) >= 0*order.getTransType().mod;
    }

    public static TransactionDetails convert(FutureOrder order) {
        TransactionDetails details = new TransactionDetails();
        details.setCurrency(order.getCurrency());
        details.setTransactionType(order.getTransType());
        details.setUnitPrice(order.getRate());
        details.setTransUnits(order.getAmount().intValue());
        return details;
    }

    public static TransactionDetails convertWithActualPrices(FutureOrder order, Prices prices) {
        order.setRate(prices.getFor(order.getTransType()));
        return convert(order);
    }
}
