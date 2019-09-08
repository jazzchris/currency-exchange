package com.jazzchris.currencyexchange.core;

import java.math.BigDecimal;

enum Currencies implements Unitable {

	USD(1, "Dollar"),
	EUR(1, "Euro"),
	CZK(100, "Czech koruna"),
	PLN(1, "Polski złoty");

	public final int unit;
	public final String fullName;

	private Currencies(int unit, String fullName) {
		this.unit = unit;
		this.fullName = fullName;
	}

	@Override
	public BigDecimal unit() {
		return BigDecimal.valueOf(unit);
	}

	@Override
	public String fullName() {
		return fullName;
	}
}
