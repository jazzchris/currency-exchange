package com.jazzchris.currencyexchange.core;

public interface PossessorDescriptor<T, E extends Enum<E> & Unitable> {

	Class<E> getEnum();
	
	Possessor<E> toPossessor(T t);
	
	void update(Possessor<E> p, T t);
}
