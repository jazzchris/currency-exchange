package com.jazzchris.currencyexchange.rabbitmq.service;

import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.stock.Stocks;


import javax.validation.Payload;
import java.util.List;

public interface FutureOrderConsumer {

    void ratingsMessage(String message);
}
