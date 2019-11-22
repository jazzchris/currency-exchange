package com.jazzchris.currencyexchange.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
public class FutureOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="users_id", nullable=false)
    @JsonBackReference
    private Users users;

    public void setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Embedded
    private TransactionDetails transactionDetails;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order order;

    public FutureOrder() {}

    public int getId() {
        return id;
    }

    public void setOrderId(int orderId) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "FutureOrder{" +
                "id=" + id +
                ", users=" + users +
                ", transactionDetails=" + transactionDetails +
                ", order=" + order +
                '}';
    }
}