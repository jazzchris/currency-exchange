package com.jazzchris.currencyexchange.core;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Abstract class which is a representation of anything that is countable and unitable.
 * Name must be described by any enum and value
 * @author Chris Ch
 *
 * @param <E> Enum type that implements Unitable interface
 */
public class Product<E extends Enum<E> & Unitable> implements Serializable {

	private static final long serialVersionUID = 6224734560026124528L;
	private final E property;
	private final BigDecimal value;

	protected Product(E property, BigDecimal value) {
		this.property = property;
		this.value = value;
	}
	
	public Product(E property, double value) {
		this(property, BigDecimal.valueOf(value));
	}
	
	protected Product(E property, int value) {
		this(property, BigDecimal.valueOf(value));
	}
	
	protected Product(E property, String value) {
		this(property, new BigDecimal(value));
	}
	
	public E getProperty() {
		return property;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	public boolean isOfProperty(E property) {
		return this.property == property;
	}
	
	static <E extends Enum<E> & Unitable> Product<E> ofZeroValue(E property) {
		return new Product<E>(property, 0);
	}
	
	public static <E extends Enum<E> & Unitable> Product<E> of(E property, double value) {
		return new Product<E>(property, value);
	}
	
	public static <E extends Enum<E> & Unitable> Product<E> of(E property, BigDecimal value) {
		return new Product<E>(property, value);
	}
	
	public Product<E> plus(Product<E> product) {
		checkProperty(this, product);
		return Product.of(property, value.add(product.getValue()));
	}
	
	public Product<E> minus(Product<E> product) {
		checkProperty(this, product);
		return Product.of(property, value.subtract(product.getValue()));
	}
	
	static <E extends Enum<E> & Unitable> void checkProperty(Product<E> p1, Product<E> p2) {
		if ( p1.getProperty() != p2.getProperty() )
			throw new IllegalArgumentException("Property must be equal");
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		return result;
	}
	
	@Override
	public final String toString() {
		String toString = String.format("%s [property=%s, value=%s]", getClass().getSimpleName(),
				getProperty(), getValue());
		return toString;
	}
}
