package com.jazzchris.currencyexchange.core;

public enum TransactionType {

	SELL(-1),
	BUY(1);
	
	public final int mod;
	
	private TransactionType(int mod) {
		this.mod = mod;
	}
}
