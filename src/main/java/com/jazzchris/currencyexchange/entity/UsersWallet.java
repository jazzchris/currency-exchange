package com.jazzchris.currencyexchange.entity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import com.jazzchris.currencyexchange.core.Product;
import com.jazzchris.currencyexchange.stock.Currency;

@Entity
public class UsersWallet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="usd")
	@Min(0)
	private BigDecimal usd;
	
	@Column(name="eur")
	@Min(0)
	private BigDecimal eur;

	@Column(name="chf")
	@Min(0)
	private BigDecimal chf;
	
	@Column(name="rub")
	@Min(0)
	private BigDecimal rub;
	
	@Column(name="czk")
	@Min(0)
	private BigDecimal czk;
	
	@Column(name="gbp")
	@Min(0)
	private BigDecimal gbp;
	
	@Column(name="pln")
	@Min(0)
	private BigDecimal pln;
	
	public UsersWallet() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getUsd() {
		return usd;
	}

	public void setUsd(BigDecimal usd) {
		this.usd = usd;
	}

	public BigDecimal getEur() {
		return eur;
	}

	public void setEur(BigDecimal eur) {
		this.eur = eur;
	}

	public BigDecimal getChf() {
		return chf;
	}

	public void setChf(BigDecimal chf) {
		this.chf = chf;
	}

	public BigDecimal getRub() {
		return rub;
	}

	public void setRub(BigDecimal rub) {
		this.rub = rub;
	}

	public BigDecimal getCzk() {
		return czk;
	}

	public void setCzk(BigDecimal czk) {
		this.czk = czk;
	}

	public BigDecimal getGbp() {
		return gbp;
	}

	public void setGbp(BigDecimal gbp) {
		this.gbp = gbp;
	}

	public BigDecimal getPln() {
		return pln;
	}

	public void setPln(BigDecimal pln) {
		this.pln = pln;
	}
	@Deprecated
	public BigDecimal getByCurrency(String currency) {
		if(currency.equals("PLN"))
			return pln;
		if(currency.equals("GBP"))
			return gbp;
		if(currency.equals("CZK"))
			return czk;
		if(currency.equals("USD"))
			return usd;
		if(currency.equals("CHF"))
			return chf;
		if(currency.equals("RUB"))
			return rub;
		if(currency.equals("EUR"))
			return eur;
		return new BigDecimal(0);
	}
	
	public BigDecimal getByCurrency(Currency currency) {
		BigDecimal result;
		switch (currency) {
		case PLN:
			result = pln;
			break;
		case GBP:
			result = gbp;
			break;
		case CZK:
			result = czk;
			break;
		case USD:
			result = usd;
			break;
		case CHF:
			result = chf;
			break;
		case RUB:
			result = rub;
			break;
		case EUR:
			result = eur;
			break;
		default:
			result = new BigDecimal(0);
		}
		return result;
	}
	@Deprecated
	public void setByCurrency(String currency, BigDecimal value) {
		if(currency.equals("PLN")) {
			this.setPln(value);
			return;
		}
		if(currency.equals("GBP")) {
			this.setGbp(value);
			return;
		}
		if(currency.equals("CZK")) {
			this.setCzk(value);
			return;
		}
		if(currency.equals("USD")) {
			this.setUsd(value);
			return;
		}
		if(currency.equals("CHF")) {
			this.setChf(value);
			return;
		}
		if(currency.equals("RUB")) {
			this.setRub(value);
			return;
		}
		if(currency.equals("EUR")) {
			this.setEur(value);
			return;
		}
	}
	
	public void setByCurrency(Currency currency, BigDecimal value) {
		switch(currency) {
		case PLN:
			this.pln = value;
			break;
		case GBP:
			this.gbp = value;
			break;
		case CZK:
			this.czk = value;
			break;
		case USD:
			this.usd = value;
			break;
		case CHF:
			this.chf = value;
			break;
		case RUB:
			this.rub = value;
			break;
		case EUR:
			this.eur = value;
			break;
		}
	}
	
	private Stream<Product<Currency>> streamCollect() {
		return Arrays.asList(Currency.values()).stream().map(e -> Product.of(e, getByCurrency(e)))
				.filter(d -> d.getValue() != null);
	}
	
	public Map<Currency, Product<Currency>> collectAll() {
		return streamCollect().collect(Collectors.toMap(Product::getProperty, p -> p));
	}

	public Map<Currency, Product<Currency>> collectIfValue() {
		return streamCollect().filter(d -> d.getValue().compareTo(BigDecimal.ZERO) > 0)
				.collect(Collectors.toMap(Product::getProperty, p -> p));
	}
	
	public Map<Currency, Product<Currency>> collectForeign() {
		return streamCollect().filter(k -> Currency.FOREIGN.containsValue(k.getProperty()))
				.collect(Collectors.toMap(Product::getProperty, p -> p));
	}
	
	@Override
	public String toString() {
		return "UsersWallet [id=" + id + ", usd=" + usd + ", eur=" + eur + ", chf=" + chf + ", rub=" + rub + ", czk="
				+ czk + ", gbp=" + gbp + ", pln=" + pln + "]";
	}


}
