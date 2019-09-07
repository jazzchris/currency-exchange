package com.jazzchris.currencyexchange.core;

import java.math.BigDecimal;

public interface Unitable {

	static final int DIGITS = 2;
	
	BigDecimal unit();
	
	default String fullName() {
		return this.toString();
	}
	
	default int digits() {
		return DIGITS;
	}
}
