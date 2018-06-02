package com.zzw.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zzw.ebook.model.Order;

import antlr.collections.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
