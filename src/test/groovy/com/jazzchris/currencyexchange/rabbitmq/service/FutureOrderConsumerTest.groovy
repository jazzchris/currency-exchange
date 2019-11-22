package com.jazzchris.currencyexchange.rabbitmq.service

import com.jazzchris.currencyexchange.core.TransactionType
import com.jazzchris.currencyexchange.entity.FutureOrder
import com.jazzchris.currencyexchange.entity.TransactionDetails
import com.jazzchris.currencyexchange.stock.Currency
import com.jazzchris.currencyexchange.stock.Prices
import spock.lang.Specification

class FutureOrderConsumerTest extends Specification {

    FutureOrder BUY
    FutureOrder SELL

    def setup() {
        BUY = new FutureOrder()
        TransactionDetails detailsBuy = new TransactionDetails()
        detailsBuy.transUnits = 20
        detailsBuy.currency = Currency.USD
        detailsBuy.unitPrice = 4.500
        detailsBuy.transactionType = TransactionType.BUY
        BUY.setTransactionDetails(detailsBuy)

        SELL = new FutureOrder()
        TransactionDetails detailsSell = new TransactionDetails()
        detailsSell.transUnits = 20
        detailsSell.currency = Currency.USD
        detailsSell.unitPrice = 4.500
        detailsSell.transactionType = TransactionType.SELL
        SELL.setTransactionDetails(detailsSell)
    }

    def "if purchase price is lower than ordered then buy is TRUE"() {
        given:
            Prices prices = new Prices()
            prices.purchasePrice = 4.000
            prices.sellPrice = 4.000
        expect:
            FutureOrderConsumerImpl.isAcceptable(BUY, prices)
    }

    def "if purchase price is higher than ordered then buy is FALSE"() {
        given:
            Prices prices = new Prices()
            prices.purchasePrice = 5.000
            prices.sellPrice = 5.000
        expect:
            !FutureOrderConsumerImpl.isAcceptable(BUY, prices)
    }

    def "if sell price is higher than ordered then sell is TRUE"() {
        given:
            Prices prices = new Prices()
            prices.purchasePrice = 5.000
            prices.sellPrice = 5.000
        expect:
            FutureOrderConsumerImpl.isAcceptable(SELL, prices)
    }

    def "if sell price is lower than ordered then sell is FALSE"() {
        given:
            Prices prices = new Prices()
            prices.purchasePrice = 4.000
            prices.sellPrice = 4.000
        expect:
            !FutureOrderConsumerImpl.isAcceptable(SELL, prices)
    }

}
