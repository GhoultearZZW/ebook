package com.zzw.ebook.Kafka;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaSender {
	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	private Gson gson = new GsonBuilder().create();
	
	public void send(String username,String password) {
		RegisterMessage message = new RegisterMessage();
		message.setUsername(username);
		message.setPassword(password);
		//message.setCategory(category);
		message.setSendTime(new Date());
		System.out.println("message:"+gson.toJson(message));
		kafkaTemplate.send("register",gson.toJson(message));
	}
}
