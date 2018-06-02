package com.zzw.ebook.Kafka;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zzw.ebook.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaReceiver {
	
	private Gson gson = new GsonBuilder().create();
	
	@Autowired
	UserService userService;
	
	@KafkaListener(topics= {"register"})
	public void listen(ConsumerRecord<?, ?> record) {
		System.out.println("Kafka---------------------------");
		Optional<?> kafkaMessage = Optional.ofNullable(record.value());
		if(kafkaMessage.isPresent()) {
			Object message = kafkaMessage.get();
			RegisterMessage messageGet = gson.fromJson(message.toString(), RegisterMessage.class);
			userService.SaveUser(messageGet.getUsername(), messageGet.getPassword(), messageGet.getCategory());
		}
	}
}
