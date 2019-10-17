package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.stock.Stocks;

public interface StocksFanoutProducer {

    void sendStocksToTransact(Stocks message, String key);

    String allertUsers(Stocks message, String key);
}
