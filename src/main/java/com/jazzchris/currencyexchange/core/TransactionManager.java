package com.jazzchris.currencyexchange.core;

import java.util.Optional;

public class TransactionManager<E extends Enum<E> & Unitable> {	
	private final boolean acceptDebit;
	private final Optional<E> settlement;
	
	private TransactionManager(Configuration<E> config) {
		this.acceptDebit = config.acceptDebit;
		this.settlement = Optional.ofNullable(config.settlement);
	}
	
	public static <E extends Enum<E> & Unitable> Configuration<E> config(Class<E> clazz) {
		return new Configuration<E>(null);
	}
	
	public static class Configuration<E extends Enum<E> & Unitable> {
		private boolean acceptDebit = false;
		private E settlement = null;
		
		private Configuration(E settlement) {
			this.settlement = settlement;
		}

		public static <E extends Enum<E> & Unitable> Configuration<E> newInstance(Class<E> clazz) {
			return new Configuration<E>(null);
		}
		
		public static <E extends Enum<E> & Unitable> Configuration<E> newFixedInstance(E settlement) {
			return new Configuration<E>(settlement);
		}
		
		public Configuration<E> acceptDebit(boolean accept) {
			this.acceptDebit = accept;
			return this;
		}
		
		public TransactionManager<E> build() {
			return new TransactionManager<E>(this);
		}
	}

	public Message proceed(Transaction<E> transaction) {
		if (!transaction.isHandleable()) {
			return new Message(FailMessage.FORBIDDEN);
		}
		Transactions.subtractProduct(transaction.getFrom(), transaction.getProduct());
		Transactions.addProduct(transaction.getTo(), transaction.getProduct());
		Transactions.subtractProduct(transaction.getTo(), transaction.getPrice());
		Transactions.addProduct(transaction.getFrom(), transaction.getPrice());
		return new Message("Success", true);
	}
	
	public Optional<E> getSettlement() {
		return settlement;
	}

	public boolean isAcceptDebit() {
		return acceptDebit;
	}
}
