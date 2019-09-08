package com.jazzchris.currencyexchange.core

import spock.lang.Specification

class TransactionsTest extends Specification {
	
	Possessor<Currencies> possessor
	Product<Currencies> product
	
	def setup() {
		possessor = new Possessor<>()
		possessor.addProduct(Product.of(Currencies.PLN, 100))
		possessor.addProduct(Product.of(Currencies.USD, 20))
		product = Product.of(Currencies.USD, 50)
	}
	
	def "adding product to possessor who already has some"() {
		when: "adding product to possessor who already has product of USD currency"
			Transactions.addProduct(possessor, product)
		then: "success"
			possessor.findProduct(Currencies.USD).get().value == 70
	}
	
	def "subtracting product to possessor who already has some"() {
		when: "subtracting product to possessor who already has product of USD currency"
			Transactions.subtractProduct(possessor, product)
		then: "success"
			possessor.findProduct(Currencies.USD).get().value == -30
	}
	
	def "adding product to possessor who does not have any"() {
		given: "new product not existing in possessor's resources"
			Product<Currencies> newProduct = Product.of(Currencies.EUR, 10)
		when: "adding product to possessor"
			Transactions.addProduct(possessor, newProduct)
		then: "NoSuchElementException will be thrown"
			thrown NoSuchElementException
	}
	
	def "subtracting product to possessor who does not have any"() {
		given: "new product not existing in possessor's resources"
			Product<Currencies> newProduct = Product.of(Currencies.EUR, 10)
		when: "subtracting product from possessor"
			Transactions.subtractProduct(possessor, newProduct)
		then: "NoSuchElementException will be thrown"
			thrown NoSuchElementException
	}
	
}
