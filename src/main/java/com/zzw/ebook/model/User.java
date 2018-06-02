package com.zzw.ebook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="users",catalog="")
public class User implements Serializable{
	private int userId;
    private String username;
    private String password;
    private String role;
    private String category;
    
    @JsonIgnore
    private Collection<OrderItem> orderItems;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="userId",nullable=false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name="username",nullable=true,length=31)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name="password",nullable=true,length=127)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name="role",nullable=true)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name="category",nullable=true)
    public String getCategory() {
    	return category;
    }
    
    public void setCategory(String category) {
    	this.category=category;
    }

    @OneToMany(mappedBy = "customer")
    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getUserId() != user.getUserId()) return false;
        if (!getUsername().equals(user.getUsername())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        return getRole().equals(user.getRole());
    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}