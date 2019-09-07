package com.jazzchris.currencyexchange.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.jazzchris.currencyexchange.stock.Currency;
import com.jazzchris.currencyexchange.validation.PasswordChecked;
import com.jazzchris.currencyexchange.validation.Unitable;

@PasswordChecked(first="password", second="checkPassword", message="password must match")
public class UserRegistration {

	@NotNull(message="required")
	@Size(min=1, message="required")
	private String username;

	@NotNull(message="required")
	@Size(min=1, message="required")
	private String firstName;
	
	@NotNull(message="required") 
	@Size(min=1, message="required")
	private String lastName;
	
	@NotNull(message="required")
	@Size(min=1, message="required")
	private String password;
	
	@NotNull(message="required")
	@Size(min=1, message="required")
	private String checkPassword;
	
	@NotNull(message="required")
	@Email
	private String email;
	
	@Unitable(currency = Currency.USD)
	private BigDecimal usd;
	
	@Unitable(currency = Currency.EUR)
	private BigDecimal eur;
	
	@Unitable(currency = Currency.RUB)
	private BigDecimal rub;
	
	@Unitable(currency = Currency.CZK)
	private BigDecimal czk;
	
	@Unitable(currency = Currency.GBP)
	private BigDecimal gbp;
	
	@Unitable(currency = Currency.CHF)
	private BigDecimal chf;
	
	@Positive(message="value must be more than 0")
	@NotNull(message="required")
	@Unitable(currency = Currency.PLN)
	private BigDecimal pln;

	public UserRegistration() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
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

	public BigDecimal getChf() {
		return chf;
	}

	public void setChf(BigDecimal chf) {
		this.chf = chf;
	}
	
	public BigDecimal getByCurrency(Currency currency) {
		switch(currency) {
			case PLN:	return pln;
			case GBP:	return gbp;
			case CZK:	return czk;
			case USD:	return usd;
			case CHF:	return chf;
			case RUB:	return rub;
			case EUR:	return eur;
			default:	throw new IllegalArgumentException("Currency not available");
		}
	}
}
