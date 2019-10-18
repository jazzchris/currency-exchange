package com.jazzchris.currencyexchange.http.client;

import com.jazzchris.currencyexchange.stock.Stocks;

public interface StocksProvider {

    Stocks fetch();
}
