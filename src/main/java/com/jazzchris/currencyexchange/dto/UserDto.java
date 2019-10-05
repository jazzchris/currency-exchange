package com.jazzchris.currencyexchange.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.jazzchris.currencyexchange.stock.Currency;

public class UserDto {

	private String fullName;
	private Map<Currency, BigDecimal> wallet;
	private BigDecimal pln;
	
	public UserDto() {}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Map<Currency, BigDecimal> getWallet() {
		return wallet;
	}

	public void setWallet(Map<Currency, BigDecimal> wallet) {
		this.wallet = wallet;
	}

	public BigDecimal getPln() {
		return pln;
	}

	public void setPln(BigDecimal pln) {
		this.pln = pln;
	}
}
