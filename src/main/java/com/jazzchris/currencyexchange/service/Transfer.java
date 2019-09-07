package com.jazzchris.currencyexchange.service;

import org.springframework.util.Assert;

import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.entity.Users;

public class Transfer {

	private Transfer() {}
	
	public static boolean add(Cash account, Cash funds) {
		if(!isSameCurrency(account, funds)) {
			throw new IllegalArgumentException("Added cash must be the same currency");
		}
		checkedAdd(account, funds);
		return true;
	}
	
	public static boolean subtract(Cash account, Cash funds) {
		if(!isSameCurrency(account, funds)) {
			throw new IllegalArgumentException("Added cash must be the same currency");
		}
		checkedSubtract(account, funds);
		return true;
	}
	
	public static boolean add(Users user, Cash funds) {
		Cash users = new Cash(funds.getCurrency(), user.getUsersWallet().getByCurrency(funds.getCurrency()));
		checkedAdd(users, funds);
		user.getUsersWallet().setByCurrency(funds.getCurrency(), users.getValue());
		return true;
	}
	
	public static boolean subtract(Users user, Cash funds) {
		Cash users = new Cash(funds.getCurrency(), user.getUsersWallet().getByCurrency(funds.getCurrency()));
		checkedSubtract(users, funds);
		user.getUsersWallet().setByCurrency(funds.getCurrency(), users.getValue());
		return true;
	}
	
	public static boolean transfer(Users from, Cash to, Cash amount) {
		add(to, amount);
		subtract(from, amount);
		return true;
	}
	
	public static boolean transfer(Cash from, Users to, Cash amount) {
		add(to, amount);
		subtract(from, amount);
		return true;
	}
	
	public static boolean isSameCurrency(Cash one, Cash two) {
		return one.getCurrency().equals(two.getCurrency());
	}
	
	private static void checkedAdd(Cash one, Cash two) {
		Assert.isTrue(one.getCurrency().equals(two.getCurrency()), "Currencies of both amounts must be the same");
		one.setValue(one.getValue().add(two.getValue()));
	}
	
	private static void checkedSubtract(Cash one, Cash two) {
		Assert.isTrue(one.getCurrency().equals(two.getCurrency()), "Currencies of both amounts must be the same");
		one.setValue(one.getValue().subtract(two.getValue()));
	}
}
