package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.http.client.StocksProvider;
import com.jazzchris.currencyexchange.rabbitmq.service.StocksFanoutProducer;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StockServiceCacheImpl implements StockService {

    @Autowired
    private StocksFanoutProducer producer;

    @Autowired
    private StocksProvider stocksProvider;
//    private Stocks stocksBefore = stocksProvider.refreshAndGetStocks();
    private static Logger logger = LoggerFactory.getLogger(StockServiceCacheImpl.class);

    @Override
    @Cacheable("stocks")
    public Stocks getStocks() {
        logger.info("-->Inside StockService#getStocks");
        Stocks stocksAfter = stocksProvider.refreshAndGetStocks();
//        if (stocksAfter.getPublicationDate().isAfter(stocksBefore.getPublicationDate())) {
//            producer.sendStocksToTransact(stocksAfter, "new-stuff");
//            stocksBefore = stocksAfter;
//        }
        return stocksAfter;
    }
}
