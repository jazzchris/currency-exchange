package com.jazzchris.currencyexchange.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import com.jazzchris.currencyexchange.core.TransactionCircs;
import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.stock.Currency;

/**
 * class keeps details about BUY and SELL transactions
 */
public class TransactionDetails implements TransactionCircs<Currency> {

	private BigDecimal unitPrice;
	private Currency currency;
	private TransactionType transactionType;
	
	@Min(value=1, message="Not valid")
	private int transUnits;
//	private LocalDateTime timeStamp;
	
	public TransactionDetails() {}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public int getTransUnits() {
		return transUnits;
	}

	public void setTransUnits(int transUnits) {
		this.transUnits = transUnits;
	}
	
	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
//	public LocalDateTime getTimeStamp() {
//		return timeStamp;
//	}
//
//	public void setTimeStamp(LocalDateTime timeStamp) {
//		this.timeStamp = timeStamp;
//	}

	@Override
	public String toString() {
		return "TransactionDetails [transUnits=" + transUnits + ", unitPrice=" + unitPrice + ", currency=" + currency
				+ ", transactionType=" + transactionType + "]";
	}


}
