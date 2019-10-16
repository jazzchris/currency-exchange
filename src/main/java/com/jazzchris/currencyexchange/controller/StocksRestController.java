package com.jazzchris.currencyexchange.controller;

import com.jazzchris.currencyexchange.service.StockService;
import com.jazzchris.currencyexchange.stock.Currency;
import com.jazzchris.currencyexchange.stock.Prices;
import com.jazzchris.currencyexchange.stock.Stocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/current-stocks", produces="application/json")
public class StocksRestController {

    private static Logger logger = LoggerFactory.getLogger(StocksRestController.class);

    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<Stocks> getCurrentStocks() {
        logger.info("--->Inside /current-stocks endpoint");
        return ResponseEntity.ok()
                .body(stockService.getStocks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prices> getCurrentPrices(@PathVariable("id") String currency) {
        logger.info("--->Inside /current-stocks/"+currency+" endpoint");
        return ResponseEntity.ok()
                .body(stockService.getStocks().getItems().get(Currency.valueOf(currency)));
    }
}
