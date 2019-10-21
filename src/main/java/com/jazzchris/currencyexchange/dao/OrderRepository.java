package com.jazzchris.currencyexchange.dao;

import com.jazzchris.currencyexchange.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
