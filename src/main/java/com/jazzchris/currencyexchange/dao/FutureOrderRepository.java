package com.jazzchris.currencyexchange.dao;

import com.jazzchris.currencyexchange.entity.*;
import com.jazzchris.currencyexchange.stock.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FutureOrderRepository extends JpaRepository<FutureOrder, Integer> {

    List<FutureOrder> findAllByUsers(Users users);

    FutureOrder findFirstByUsers(Users users);

    List<FutureOrder> findAllByTransactionDetailsCurrency(Currency currency);

    List<FutureOrder> findAllByOrder(Order order);

    @Query("SELECT u FROM FutureOrder u WHERE u.order = null")
    List<FutureOrder> findAllAwaited();
}
