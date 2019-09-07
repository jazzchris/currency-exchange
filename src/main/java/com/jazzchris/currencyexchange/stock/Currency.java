package com.jazzchris.currencyexchange.stock;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jazzchris.currencyexchange.core.Unitable;

public enum Currency implements Unitable {
	
	USD("US Dollar", 1),
	EUR("Euro", 1),
	CHF("Swiss Franc", 1),
	RUB("Russian ruble", 100),
	CZK("Czech koruna", 100),
	GBP("Pound sterling", 1),
	PLN("Polski zloty", 1) {
		@Override
		public BigDecimal unit() {
			return BigDecimal.valueOf(0.01);
		}
	};

	/**
	 * settlement currency which equals <b><i>PLN</i></b>
	 */
	public static final Currency SETTLEMENT = PLN;
	
	/**
	 * map of only foreign currencies (without <b><i>PLN</i></b>)
	 */
	public static final Map<String, Currency> FOREIGN = new LinkedHashMap<>();
	static {
		for (Currency c: values()) {
			if (c==SETTLEMENT)
				break;
			FOREIGN.put(c.name(), c);
		}
	}

	public final int unit;
	public final String fullName;
	
	private Currency(String fullName, int unit) {
		this.fullName = fullName;
		this.unit = unit;
	}
	
	public int getUnit() {
		return this.unit;
	}
	
	public static Currency getCurrency(String name) {
		return FOREIGN.get(name.toUpperCase());
	}

	@Override
	public BigDecimal unit() {
		return BigDecimal.valueOf(unit);
	}
}
