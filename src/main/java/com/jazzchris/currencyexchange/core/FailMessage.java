package com.jazzchris.currencyexchange.core;

enum FailMessage {
	
	CANNOT_CONNECT("Cannot connect to server"),
	RATES_NOT_ACTUAL("Currency rates are not actual"),
	USER_NO_FUNDS("Not enough funds on your account"),
	OFFICE_NO_FUNDS("Not enough funds on Currency Office account"),
	FORBIDDEN("Transaction failed");
	
	public final String text;
	
	private FailMessage(String text) {
		this.text = text;
	}
}
