package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.http.client.StocksProvider;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StockServiceCacheImpl implements StockService {

    @Autowired
    private StocksProvider stocksProvider;
    private static Logger logger = LoggerFactory.getLogger(StockServiceCacheImpl.class);

    @Override
    @Cacheable("stocks")
    public Stocks getStocks() {
        logger.info("-->Inside StockService#getStocks");
        return stocksProvider.refreshAndGetStocks();
    }
}
