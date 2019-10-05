package com.jazzchris.currencyexchange.core;

public interface EntityConverter<T, D> {

	D convert(T from);
}
