package com.jazzchris.currencyexchange.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;

import com.jazzchris.currencyexchange.core.TransactionCircs;
import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.stock.Currency;

/**
 * class keeps details about BUY and SELL transactions
 */
@Embeddable
public class TransactionDetails implements TransactionCircs<Currency> {

	@Column(name="rate")
	private BigDecimal unitPrice;

	@Column(name="currency")
	@Enumerated(EnumType.STRING)
	private Currency currency;

	@Column(name="transType")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Column(name="amount")
	@Min(value=1, message="Not valid")
	private int transUnits;
	
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

	@Override
	public String toString() {
		return "TransactionDetails [transUnits=" + transUnits + ", unitPrice=" + unitPrice + ", currency=" + currency
				+ ", transactionType=" + transactionType + "]";
	}


}
