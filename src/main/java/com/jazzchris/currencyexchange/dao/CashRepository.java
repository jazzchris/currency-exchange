package com.jazzchris.currencyexchange.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.stock.Currency;

public interface CashRepository extends JpaRepository<Cash, String> {
	
	Cash findByCurrency(Currency curr);
	
	List<Cash> findAllByCurrency(Iterable<Currency> curr);
	
}
