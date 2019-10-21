package com.jazzchris.currencyexchange.service;

import com.jazzchris.currencyexchange.dao.FutureOrderRepository;
import com.jazzchris.currencyexchange.entity.FutureOrder;
import com.jazzchris.currencyexchange.entity.Status;
import com.jazzchris.currencyexchange.entity.TransactionDetails;
import com.jazzchris.currencyexchange.entity.Users;
import com.jazzchris.currencyexchange.stock.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FutureOrderServiceImpl implements FutureOrderService {

    @Autowired
    private FutureOrderRepository futureOrderRepository;

    @Override
    public List<FutureOrder> findAllByUser(Users users) {
        return futureOrderRepository.findAllByUsers(users);
    }

    @Override
    public List<FutureOrder> findAllAwaited() {
        return futureOrderRepository.findAllByOrder(null);
    }

//    @Override
 //   public List<FutureOrder> findAllByStatus(Status status) {
 //       return futureOrderRepository.findAllByStatus(status);
 //   }

    @Override
    public List<FutureOrder> findAllByCurrency(Currency currency) {
        return futureOrderRepository.findAllByTransactionDetailsCurrency(currency);
    }

    @Override
    public FutureOrder findFirstByUser(Users users) {
        return futureOrderRepository.findFirstByUsers(users);
    }

    @Override
    public void save(FutureOrder futureOrder) {
        futureOrderRepository.save(futureOrder);
    }

    @Override
    public void save(Users user, TransactionDetails details) {
//        FutureOrder order = convert(user, details, Status.AWAIT);
//        futureOrderRepository.save(order);
    }

    @Override
    public List<FutureOrder> findAll() {
        return futureOrderRepository.findAll();
    }

    @Override
    public Optional<FutureOrder> findById(int id) {
        return futureOrderRepository.findById(id);
    }


//    public FutureOrder convert(Users user, TransactionDetails details, Status status) {
//        FutureOrder order = new FutureOrder();
//        order.setUsers(user);
//        order.setCurrency(details.getCurrency());
//        order.setAmount(BigDecimal.valueOf(details.getTransUnits()));
//        order.setRate(details.getUnitPrice());
//        order.setTransType(details.getTransactionType());
//        order.setOrder(null);
//        return order;
//    }


}
