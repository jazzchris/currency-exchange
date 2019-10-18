package com.jazzchris.currencyexchange.stock;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jazzchris.currencyexchange.core.TransactionType;

/**
 * keeps <b>purchase</b>, <b>sell</b> and <b>average</b> price of currency
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prices implements Serializable {

	private BigDecimal purchasePrice;
	private BigDecimal sellPrice;
	private BigDecimal averagePrice;
	
	public Prices() {}
	
	public Prices(BigDecimal purchasePrice, BigDecimal sellPrice, BigDecimal averagePrice) {
		this.purchasePrice = purchasePrice;
		this.sellPrice = sellPrice;
		this.averagePrice = averagePrice;
	}
	
	public BigDecimal getFor(TransactionType type) {
		switch(type) {
		case BUY:	return getSellPrice();
		case SELL:	return getPurchasePrice();
		default:	throw new IllegalArgumentException("Unknown Transaction type");
		}
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	@Override
	public String toString() {
		return "Prices [purchasePrice=" + purchasePrice + ", sellPrice=" + sellPrice + ", averagePrice=" + averagePrice
				+ "]";
	}
	
}
