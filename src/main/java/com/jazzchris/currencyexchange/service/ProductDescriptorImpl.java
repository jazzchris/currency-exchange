package com.jazzchris.currencyexchange.service;

import java.util.List;
import java.util.stream.Collectors;

import com.jazzchris.currencyexchange.core.Possessor;
import com.jazzchris.currencyexchange.core.PossessorDescriptor;
import com.jazzchris.currencyexchange.core.Product;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.entity.UsersWallet;
import com.jazzchris.currencyexchange.stock.Currency;

public class ProductDescriptorImpl implements PossessorDescriptor<Users, Currency> {

	@Override
	public Possessor<Currency> toPossessor(Users user) {
		return new Possessor<>(user.getUsersWallet().collectAll());
	}

	@Override
	public Class<Currency> getEnum() {
		return Currency.class;
	}

	@Override
	public void update(Possessor<Currency> poss, Users user) {
		getProducts(poss).stream().forEach(p -> user.getUsersWallet().setByCurrency(p.getProperty(), p.getValue()));
	}
	
	private List<Product<Currency>> getProducts(Possessor<Currency> poss) {
		return poss.getAll().values().stream().filter(p -> p.getValue() != null)
				.collect(Collectors.toList());
	}

	public Users fromPossessor(Possessor<Currency> poss) {
		UsersWallet theWallet = new UsersWallet();
		List<Product<Currency>> currs = getProducts(poss);
		currs.stream().forEach(p -> theWallet.setByCurrency(p.getProperty(), p.getValue()));
		Users user = new Users();
		user.setUsersWallet(theWallet);
		return user;
	}

}
