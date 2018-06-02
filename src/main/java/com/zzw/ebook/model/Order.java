package com.zzw.ebook.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;


@Entity
@Table(name="t_order",catalog = "")
public class Order {
    private int orderId;
    private Timestamp date;
    private String status;
    private Collection<OrderItem> orderItems;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderId",nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @JoinColumn(name="date",nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @JoinColumn(name="status",nullable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "order")
    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}