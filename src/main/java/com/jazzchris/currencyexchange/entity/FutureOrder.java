package com.jazzchris.currencyexchange.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jazzchris.currencyexchange.core.TransactionType;
import com.jazzchris.currencyexchange.stock.Currency;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class FutureOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name="users_id", nullable=false)
    @JsonBackReference
    private Users users;

    @Column(name="currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name="rate")
    private BigDecimal rate;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="transType")
    @Enumerated(EnumType.STRING)
    private TransactionType transType;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public FutureOrder() {}

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransType() {
        return transType;
    }

    public void setTransType(TransactionType transType) {
        this.transType = transType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FutureOrder{" +
                "orderId=" + orderId +
                ", users=" + users +
                ", currency=" + currency +
                ", rate=" + rate +
                ", amount=" + amount +
                ", transType=" + transType +
                ", status=" + status +
                '}';
    }
}
