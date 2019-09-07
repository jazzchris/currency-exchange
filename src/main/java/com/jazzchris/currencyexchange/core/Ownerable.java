package com.jazzchris.currencyexchange.core;

import java.util.Optional;

public interface Ownerable<E extends Enum<E> & Unitable> {

	Optional<Product<E>> findProduct(E u);
	
	void setProduct(Product<E> p);
}
