package com.jazzchris.currencyexchange.rabbitmq.service

import com.jazzchris.currencyexchange.core.TransactionType
import com.jazzchris.currencyexchange.entity.FutureOrder
import com.jazzchris.currencyexchange.entity.Status
import com.jazzchris.currencyexchange.stock.Currency
import com.jazzchris.currencyexchange.stock.Prices
import spock.lang.Specification

class FutureOrderConsumerTest extends Specification {

    FutureOrder buyOrder
    FutureOrder sellOrder

    def setup() {
        buyOrder = new FutureOrder()
        buyOrder.amount = 20
        buyOrder.currency = Currency.USD
        buyOrder.rate = 4.500
        buyOrder.transType = TransactionType.BUY
        buyOrder.status = Status.AWAIT

        sellOrder = new FutureOrder()
        sellOrder.amount = 20
        sellOrder.currency = Currency.USD
        sellOrder.rate = 4.500
        sellOrder.transType = TransactionType.BUY
        sellOrder.status = Status.AWAIT
    }

    def "testing isAcceptable method"() {
        given:
            Prices prices = new Prices()
            prices.purchasePrice = 4.000
            prices.sellPrice = 5.000
        expect:
            FutureOrderConsumerImpl.isAcceptable(sellOrder, prices)
            FutureOrderConsumerImpl.isAcceptable(buyOrder, prices)
    }
}
