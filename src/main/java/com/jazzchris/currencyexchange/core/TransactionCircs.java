package com.jazzchris.currencyexchange.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * class keeps details about BUY and SELL transactions
 */
public interface TransactionCircs<E extends Enum<E> & Unitable> {

	public BigDecimal getUnitPrice();

	public E getCurrency();

	public int getTransUnits();

	public TransactionType getTransactionType();

}
