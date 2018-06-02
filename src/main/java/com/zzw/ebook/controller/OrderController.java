package com.zzw.ebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zzw.ebook.model.Order;
import com.zzw.ebook.model.OrderItem;
import com.zzw.ebook.model.User;
import com.zzw.ebook.service.OrderService;
import com.zzw.ebook.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	
	@GetMapping("/checkOrder/{username}")
	public String getOrders(@PathVariable("username")String username,Model model) {
		User user = userService.getUserByUsername(username);
		List<Order> list = orderService.findAll();
		List<OrderItem> detail = new ArrayList<>();
		List<Map<String, String>> result = new ArrayList<>();
		for(Order item : list) {
			detail = (List<OrderItem>)item.getOrderItems();
			for(OrderItem detailItem : detail) {
				if(detailItem.getCustomer().getUsername().equals(username)) {
					Map<String, String> map = new HashMap<>();
					
					//get the result map
					map.put("name", detailItem.getBook().getTitle());
					map.put("price", String.valueOf(detailItem.getPrice()));
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put("date", sdf.format(item.getDate()));
					map.put("status", item.getStatus());
					result.add(map);
					break;
				}
			}
		}
		System.out.println(result.size());
		model.addAttribute("orders",result);
		model.addAttribute("username",username);
		return "Orders";
	}
}
