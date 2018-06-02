package com.zzw.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zzw.ebook.model.Order;
import com.zzw.ebook.model.OrderItem;

import java.util.*;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	@Query(value="select * from order_items where customer_id =:userid",nativeQuery=true)
	List<OrderItem> getOrders(@Param("userid")int userid);
}
