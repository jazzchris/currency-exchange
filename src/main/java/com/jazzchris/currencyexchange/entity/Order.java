package com.jazzchris.currencyexchange.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.stock.Currency;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="users_id", nullable=false)
    @JsonBackReference
    private Users users;

    @Embedded
    private TransactionDetails transactionDetails;

//    @Column(name="currency")
//    @Enumerated(EnumType.STRING)
//    private Currency currency;
//
//    @Column(name="rate")
//    private BigDecimal rate;
//
//    @Column(name="amount")
//    private BigDecimal amount;
//
//    @Column(name="type")
//    @Enumerated(EnumType.STRING)
//    private TransactionType type;

    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

//    public Currency getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(Currency currency) {
//        this.currency = currency;
//    }
//
//    public BigDecimal getRate() {
//        return rate;
//    }
//
//    public void setRate(BigDecimal rate) {
//        this.rate = rate;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public TransactionType getType() {
//        return type;
//    }
//
//    public void setType(TransactionType type) {
//        this.type = type;
//    }
}
