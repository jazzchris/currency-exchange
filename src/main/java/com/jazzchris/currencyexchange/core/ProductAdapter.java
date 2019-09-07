package com.jazzchris.currencyexchange.core;

public interface ProductAdapter<T, D extends Product<?>> {

	D convertFrom(T product);
	
	T convertTo(D product);
}
