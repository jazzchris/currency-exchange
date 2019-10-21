package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.dao.OrderRepository;
import com.jazzchris.currencyexchange.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}
