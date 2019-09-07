package com.jazzchris.currencyexchange.core;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Possessor<E extends Enum<E> & Unitable> {
	
	private final Map<E, Product<E>> resources;
	
	public Possessor() {
		resources = new ConcurrentHashMap<E, Product<E>>();
	}
	
	public Possessor(Map<E, Product<E>> resources) {
		this.resources = new ConcurrentHashMap<>(resources);
	}
	
	public final Optional<Product<E>> findProduct(E property) {
		return Optional.ofNullable(resources.get(property));
	}
	
	public Map<E, Product<E>> getAll() {
		return new ConcurrentHashMap<>(resources);
	}
	
	public final Possessor<E> addProduct(Product<E> product) {
		resources.putIfAbsent(product.getProperty(), product);
		return this;
	}
	
	public final Possessor<E> putProduct(Product<E> product) {
		resources.put(product.getProperty(), product);
		return this;
	}
	
	public static <E extends Enum<E> & Unitable> Possessor<E> convert(Ownerable<E> entity) {
		return null;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[resources=" + resources + "]";
	}
}
