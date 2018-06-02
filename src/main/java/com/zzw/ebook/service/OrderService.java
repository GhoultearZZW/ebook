package com.zzw.ebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzw.ebook.model.Order;
import com.zzw.ebook.model.OrderItem;
import com.zzw.ebook.repository.OrderItemRepository;
import com.zzw.ebook.repository.OrderRepository;

import java.util.*;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrderItemRepository orderItemRepo;
	
	public void saveOrder(Order order) {
		orderRepo.saveAndFlush(order);
	}
	
	public void saveOrderItem(OrderItem orderItem) {
		orderItemRepo.saveAndFlush(orderItem);
	}
	
	public List<OrderItem> getOrder(int userid){
		return orderItemRepo.getOrders(userid);
	}
	
	public List<Order> findAll(){
		return orderRepo.findAll();
	}
}
