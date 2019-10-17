package com.jazzchris.currencyexchange.dao;

import com.jazzchris.currencyexchange.entity.Cash;
import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.Status;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.stock.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FutureOrderRepository extends JpaRepository<FutureOrder, Integer> {

    List<FutureOrder> findAllByUsers(Users users);

    FutureOrder findFirstByUsers(Users users);

    List<FutureOrder> findAllByStatus(Status status);

    List<FutureOrder> findAllByCurrency(Currency currency);
}
