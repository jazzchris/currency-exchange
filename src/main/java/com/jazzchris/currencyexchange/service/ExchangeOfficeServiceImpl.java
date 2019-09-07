package com.jazzchris.currencyexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jazzchris.currencyexchange.dao.CashRepository;
import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.stock.Currency;

@Service
public class ExchangeOfficeServiceImpl implements ExchangeOfficeService {

	@Autowired
	private CashRepository cashRepository;
	
	@Override
	public Cash findCashByCurrency(Currency currency) {
		return cashRepository.findByCurrency(currency);
	}

	@Override
	public void save(Cash cash) {
		cashRepository.save(cash);
	}
}
