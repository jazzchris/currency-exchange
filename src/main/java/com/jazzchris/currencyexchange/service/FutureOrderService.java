package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.Status;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.stock.Currency;

import java.util.List;
import java.util.Optional;

public interface FutureOrderService {

    List<FutureOrder> findAllByUser(Users users);

    List<FutureOrder> findAllByStatus(Status status);

    List<FutureOrder> findAllByCurrency(Currency currency);

    FutureOrder findFirstByUser(Users users);

    void save(FutureOrder futureOrder);

    void save(Users user, TransactionDetails details);

    List<FutureOrder> findAll();

    Optional<FutureOrder> findById(int id);
}
