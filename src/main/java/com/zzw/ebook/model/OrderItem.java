package com.zzw.ebook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="orderItems",catalog = "")
public class OrderItem {
    private int OrderItemId;
    private Book book;
    private User customer;
    private int amount;
    private double price;

    @JsonIgnore
    private Order order;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId",nullable = false)
    public int getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(int orderItemmId) {
        OrderItemId = orderItemmId;
    }

    @ManyToOne
    @JoinColumn(name="bookId",referencedColumnName = "bookId")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @ManyToOne
    @JoinColumn(name="customerId",referencedColumnName = "userId")
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Basic
    @JoinColumn(name="amount",nullable = false)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Basic
    @JoinColumn(name="price",nullable = true)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @ManyToOne
    @JoinColumn(name = "orderId",referencedColumnName = "orderId")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
