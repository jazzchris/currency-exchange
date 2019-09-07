package com.jazzchris.currencyexchange.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import com.jazzchris.currencyexchange.stock.Currency;

@Entity
public class Cash {

	@Id
	@Column(name="currency")
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Column(name="value")
	@Min(0)
	private BigDecimal value;
	
	public Cash() {}

	public Cash(Currency currency, BigDecimal value) {
		this.currency = currency;
		this.value = value;
	}
	
	public Cash(Currency currency, double value) {
		this.currency = currency;
		this.value = BigDecimal.valueOf(value);
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public Cash add(Cash cash) {
		if(!this.currency.equals(cash.getCurrency())) {
			throw new IllegalArgumentException("Added cash must be the same currency");
		}
		BigDecimal newValue = this.value.add(cash.getValue());
		return new Cash(cash.getCurrency(), newValue);
	}
	
	public Cash subtract(Cash cash) {
		if(!this.currency.equals(cash.getCurrency())) {
			throw new IllegalArgumentException("Subtracted cash must be the same currency");
		} else {
			this.value.subtract(cash.getValue());
		}
		return this;
	}

	@Override
	public String toString() {
		return "Cash [currency=" + currency + ", value=" + value + "]";
	}
	
	public String toText() {
		return value + " " + currency;
	}
}
