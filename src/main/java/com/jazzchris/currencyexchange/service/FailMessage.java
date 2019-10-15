package com.jazzchris.currencyexchange.service;

import java.util.Arrays;

public enum FailMessage {

	CANNOT_CONNECT("Cannot connect to server"),
	RATES_NOT_ACTUAL("Currency rates are not actual"),
	USER_NO_FUNDS("Not enough funds on your account"),
	OFFICE_NO_FUNDS("Not enough funds on Currency Office account");

	public final String text;

	public static FailMessage parse(String textToParse) {
		return Arrays.asList(FailMessage.values())
				.stream().filter(p -> p.text == textToParse).findFirst().get();
	}

	private FailMessage(String text) {
		this.text = text;
	}
}
