package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.stock.Stocks;

public interface StocksFanoutProducer {

    void sendStocksToTransact(String message, String key);
}
