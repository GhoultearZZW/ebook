package com.zzw.ebook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name="books",catalog = "")
public class Book {
    private int bookId;
    private String title;
    private String author;
    private double price;
    private String publisher;
    private int	remaining; 
    private String category;
    private Timestamp date;

    @JsonIgnore
    private Collection<OrderItem> orderItem;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookId",nullable=false)
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "remaining",nullable = true)
    public int getRemaining() {
    	return remaining;
    }
    
    public void setRemaining(int remaining) {
    	this.remaining = remaining;
    }
    
    @Basic
    @Column(name="title",nullable=true,length=31)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name="author",nullable=true,length=31)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name="price",nullable=true)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name="publisher",nullable=true,length=31)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name="date",nullable=true)
    public Timestamp getData() {
        return date;
    }

    public void setData(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name="category",nullable=true)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "book")
    public Collection<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Collection<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getBookId() != book.getBookId()) return false;
        if (Double.compare(book.getPrice(), getPrice()) != 0) return false;
        if (!getTitle().equals(book.getTitle())) return false;
        if (!getAuthor().equals(book.getAuthor())) return false;
        if (!getPublisher().equals(book.getPublisher())) return false;
        return getData().equals(book.getData());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getBookId();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getPublisher().hashCode();
        result = 31 * result + getData().hashCode();
        return result;
    }
}
