package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.http.client.StocksProvider;
import com.jazzchris.currencyexchange.rabbitmq.service.StocksFanoutProducer;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StockServiceCacheImpl implements StockService {

    private static Logger logger = LoggerFactory.getLogger(StockServiceCacheImpl.class);

    @Autowired
    private StocksFanoutProducer producer;

    @Autowired
    private StocksProvider stocksProvider;

    private Stocks stocksBefore;

    @Value("${routing.stocks}")
    private String routingStocks;

    @Value("${routing.stocks.beta}")
    private String routingStocksBeta;

    @PostConstruct
    public void init() {
        this.stocksBefore = stocksProvider.fetch();
        fanout();
//        producer.sendStocksToTransact(stocksBefore.getPublicationDate().toString(), routingStocks);
//        producer.sendStocksToTransact(stocksBefore.getPublicationDate().toString(), routingStocksBeta);
    }

    @Override
    @Cacheable("stocks")
    public Stocks getStocks() {
        logger.info("Stocks Cache is expired. Receiving new stocks");
        Stocks stocksAfter = stocksProvider.fetch();
        if (stocksAfter.getPublicationDate().isAfter(stocksBefore.getPublicationDate())) {
            fanout();
//            producer.sendStocksToTransact(stocksAfter.getPublicationDate().toString(), routingStocks);
//            producer.sendStocksToTransact(stocksAfter.getPublicationDate().toString(), routingStocksBeta);
            stocksBefore = stocksAfter;
        }
        return stocksAfter;
    }

    private void fanout() {
        producer.sendStocksToTransact(stocksBefore.getPublicationDate().toString(), routingStocks);
        producer.sendStocksToTransact(stocksBefore.getPublicationDate().toString(), routingStocksBeta);
    }
}
