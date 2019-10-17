package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.stock.Stocks;

import java.util.List;

public interface FutureOrderConsumer {

    List<FutureOrder> ratingsMessage(Stocks message);
}
