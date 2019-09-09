package com.jazzchris.currencyexchange.core

import spock.lang.Specification

class TransactionTest extends Specification {
	
	Transaction transaction
	Possessor<Currencies> possOne
	Possessor<Currencies> possTwo
	def pln = Product.of(Currencies.PLN, 100)
	def usd = Product.of(Currencies.USD, 100)
	def plnLess = Product.of(Currencies.PLN, 50)
	def usdLess = Product.of(Currencies.USD, 50)
	def plnMore = Product.of(Currencies.PLN, 150)
	def usdMore = Product.of(Currencies.USD, 150)
	
	def setup() {
		possOne = new Possessor()
		possTwo = new Possessor()
		possOne.addProduct(pln)
		possOne.addProduct(usd)
		possTwo.addProduct(pln)
		possTwo.addProduct(usd)
	}
	
	def "hasEnoughMoney method successful test"() {
		given:
			transaction = Transaction.newTransactionFrom(possOne)
				.to(possTwo)
				.price(plnLess)
				.product(usdLess)
				.prepare()
		expect:
			transaction.hasEnoughMoney() == true
			transaction.isHandleable() == true
	}
	
	def "hasEnoughMoney method failure test"() {
		given:
			transaction = Transaction.newTransactionFrom(possOne)
				.to(possTwo)
				.price(plnMore)
				.product(usdLess)
				.prepare()
		expect:
			transaction.hasEnoughMoney() == false
			transaction.isHandleable() == false
	}
	
	def "hasEnoughProduct method successful test"() {
		given:
			transaction = Transaction.newTransactionFrom(possOne)
				.to(possTwo)
				.price(plnLess)
				.product(usdLess)
				.prepare()
		expect:
			transaction.hasEnoughProduct() == true
			transaction.isHandleable() == true
	}
	
	def "hasEnoughProduct method failure test"() {
		given:
			transaction = Transaction.newTransactionFrom(possOne)
				.to(possTwo)
				.price(plnLess)
				.product(usdMore)
				.prepare()
		expect:
			transaction.hasEnoughProduct() == false
			transaction.isHandleable() == false
	}
}
