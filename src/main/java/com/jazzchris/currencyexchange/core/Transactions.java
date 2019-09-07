package com.jazzchris.currencyexchange.core;

/**
 * <p>Utility class that consists of just static methods.
 * As argument always takes {@code Possessor} instance and, if included, proper {@code Product} argument.</p>
 * <p>To ensure consistency, {@code Possessor} must have the {@code Product} of request and/or settlement currency.</p>
 * @author Chris Ch
 *
 */
public class Transactions {

	private Transactions() {}
	
	/**
	 * Adds product to Possessor
	 * @param possessor
	 * @param augend
	 * @throws NullPointerException if {@code Possessor} doesn't have explicit value of given {@code Product}
	 */
	public static <E extends Enum<E> & Unitable> void addProduct(Possessor<E> possessor, Product<E> augend) {
		Product<E> newValue = possessor.findProduct(augend.getProperty()).get().plus(augend);
		possessor.putProduct(newValue);
	}
	
	/**
	 * Subtracts product from Possessor
	 * @param possessor
	 * @param subtrahend
	 * @throws NullPointerException if {@code Possessor} doesn't have explicit value of given {@code Product}
	 */
	public static <E extends Enum<E> & Unitable> void subtractProduct(Possessor<E> possessor, Product<E> subtrahend) {
		Product<E> newValue = possessor.findProduct(subtrahend.getProperty()).get().minus(subtrahend);
		possessor.putProduct(newValue);
	}
}
