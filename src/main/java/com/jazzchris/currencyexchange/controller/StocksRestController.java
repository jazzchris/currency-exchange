package com.jazzchris.currencyexchange.controller;

import com.jazzchris.currencyexchange.http.client.StocksProvider;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@RestController
@EnableCaching
public class StocksRestController {

    private static Logger logger = LoggerFactory.getLogger(StocksRestController.class);

    @Autowired
    private StocksProvider stocksProvider;

    @GetMapping("/current-stocks")
    ResponseEntity<Stocks> getCurrentStocks() {
        logger.info("--->Inside /current-stocks endpoint");
        CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(stocksProvider.refreshAndGetStocks());
    }
}
