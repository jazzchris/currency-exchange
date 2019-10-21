package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class ScheduledStock {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledStock.class);

    @Autowired
    private StockService stockServiceCacheImpl;

    @Scheduled(fixedRate = 60000, initialDelay = 10000)
    public Stocks getScheduledStocks() {
        logger.info("Refreshing stocks by scheduler");
        return stockServiceCacheImpl.getStocks();
    }
}
