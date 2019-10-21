package com.jazzchris.currencyexchange.dao;

import com.jazzchris.currencyexchange.entity.*;
import com.jazzchris.currencyexchange.stock.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FutureOrderRepository extends JpaRepository<FutureOrder, Integer> {

    List<FutureOrder> findAllByUsers(Users users);

    FutureOrder findFirstByUsers(Users users);

    //List<FutureOrder> findAllByStatus(Status status);

    List<FutureOrder> findAllByTransactionDetailsCurrency(Currency currency);

    List<FutureOrder> findAllByOrder(Order order);
}
