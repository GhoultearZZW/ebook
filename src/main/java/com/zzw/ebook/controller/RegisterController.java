package com.zzw.ebook.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zzw.ebook.Kafka.RegisterMessage;
import com.zzw.ebook.model.User;
import com.zzw.ebook.service.BookService;

@Controller
public class RegisterController {
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    BookService bookService;
	
	private Gson gson = new GsonBuilder().create();
	
	@PostMapping("/register")
	public String send(@ModelAttribute User user,Model model) {
		RegisterMessage message = new RegisterMessage();
		message.setUsername(user.getUsername());
		message.setPassword(user.getPassword());
		message.setCategory(user.getCategory());
		message.setSendTime(new Date());
		System.out.println("message:"+gson.toJson(message));
		kafkaTemplate.send("register",gson.toJson(message));
		model.addAttribute("username",user.getUsername());
		model.addAttribute("bookList",bookService.getBooks());
		return "home";
	}
}
