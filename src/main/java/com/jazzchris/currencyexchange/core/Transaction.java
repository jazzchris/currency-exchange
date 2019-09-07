package com.jazzchris.currencyexchange.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class Transaction<E extends Enum<E> & Unitable> {
	
	private final Possessor<E> from;
	private final Possessor<E> to;
	private final Product<E> product;
	private final Product<E> price;
	private final static String REQUIRED = " is required";
	
	private Transaction(Possessor<E> from, Possessor<E> to, Product<E> product, Product<E> price) {
		this.from = from;
		this.to = to;
		this.product = product;
		this.price = price;
	}

	public Possessor<E> getFrom() {
		return from;
	}

	public Possessor<E> getTo() {
		return to;
	}

	public Product<E> getProduct() {
		return product;
	}

	public Product<E> getPrice() {
		return price;
	}
	
	public static <E extends Enum<E> & Unitable> Builder<E> newTransactionFrom(Possessor<E> from) {
		return new Builder<E>(from);
	}
	
	public static <E extends Enum<E> & Unitable> Fixed<E> ofSettlement(E settlement) {
		return new Fixed<E>(settlement);
	}
	
	public boolean hasEnoughProduct() {
		Product<E> present = from.findProduct(product.getProperty()).orElseThrow(IllegalArgumentException::new);
		return present.getValue().compareTo(product.getValue()) >= 0;
	}
	
	public boolean hasEnoughMoney() {
		Product<E> present = to.findProduct(price.getProperty()).orElseThrow(IllegalArgumentException::new);
		return present.getValue().compareTo(price.getValue()) >= 0;
	}
	
	public boolean isHandleable() {
		return this.hasEnoughProduct() && this.hasEnoughMoney();
	}
	
	void addProduct(Possessor<E> poss, Product<E> product) {
		poss.addProduct(product);
	}
	
	public static class Fixed<E extends Enum<E> & Unitable> {
		private static MathContext context = new MathContext(2, RoundingMode.HALF_UP);
		
		private E settlement;
		private Possessor<E> office;
		private Possessor<E> customer;
		private Product<E> product;
		private Product<E> price;
		
		public Fixed(E settlement) {
			this.settlement = settlement;
		}
		
		public Fixed<E> currencyOffice(Possessor<E> office) {
			this.office = office;
			return this;
		}
		
		public Fixed<E> customer(Possessor<E> customer) {
			this.customer = customer;
			return this;
		}
		
		public Fixed<E> details(TransactionCircs<E> details) {
			BigDecimal unitsToTransact = BigDecimal.valueOf(details.getTransUnits());
			Product<E> settl = Product.of(settlement, 
					details.getUnitPrice().multiply(unitsToTransact).round(context));
			Product<E> foreign = Product.of(details.getCurrency(), unitsToTransact.multiply(details.getCurrency().unit()));
			switch(details.getTransactionType()) {
			case BUY:
				this.product = foreign;
				this.price = settl;
				break;
			case SELL:
				this.product = settl;
				this.price = foreign;
				break;
			default: throw new IllegalArgumentException("Unknow transaction type");
			}
			return this;
		}
		
		public Transaction<E> prepare() {
			if ( settlement == product.getProperty() ) {
				return new Transaction<E>(Objects.requireNonNull(customer, "from" + REQUIRED), 
						Objects.requireNonNull(office, "to" + REQUIRED), 
						Objects.requireNonNull(product, "product" + REQUIRED), 
						Objects.requireNonNull(price, "price" + REQUIRED));	
			}
			else {
				return new Transaction<E>(Objects.requireNonNull(office, "from" + REQUIRED), 
						Objects.requireNonNull(customer, "to" + REQUIRED), 
						Objects.requireNonNull(product, "product" + REQUIRED), 
						Objects.requireNonNull(price, "price" + REQUIRED));	
			}
		}	
	}
	
	public static class Builder<E extends Enum<E> & Unitable> {
		private Possessor<E> from;
		private Possessor<E> to;
		private Product<E> product;
		private Product<E> price;
		
		public Builder(Possessor<E> from) {
			this.from = from;
		}
		
		public Builder<E> to(Possessor<E> to) {
			this.to = to;
			return this;
		}
		
		public Builder<E> product(Product<E> product) {
			this.product = product;
			return this;
		}
		
		public Builder<E> price(Product<E> price) {
			this.price = price; 
			return this;
		}

		public Transaction<E> prepare() {
			return new Transaction<E>(Objects.requireNonNull(from, "from" + REQUIRED), 
					Objects.requireNonNull(to, "to" + REQUIRED), 
					Objects.requireNonNull(product, "product" + REQUIRED), 
					Objects.requireNonNull(price, "price" + REQUIRED));	
		}	
	}

	@Override
	public String toString() {
		return "Transaction [from=" + from + ", to=" + to + ", product=" + product + ", price=" + price + "]";
	}
}
