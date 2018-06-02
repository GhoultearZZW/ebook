package com.zzw.ebook.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.classmate.util.ResolvedTypeCache.Key;
import com.zzw.ebook.model.User;
import com.zzw.ebook.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    private RedisTemplate<String,String> template;
    
    
    public User CheckUser(String username,String password){
    	User user = new User();
    	System.out.println("here2-------------------");
    	if(!template.hasKey(username)) {
    		System.out.println("here3-------------------");
    		user = userRepo.getUserByUserName(username,password);
    		Map<String, String> map = new HashMap<>();
    		map.put("category",user.getCategory());
    		map.put("role", user.getRole());
    		map.put("password", password);
    		template.opsForHash().putAll(username, map);
    		System.out.println("not from redis");
    	}
    	else {
    		System.out.println("here5-------------------");
    		String category = (String)template.opsForHash().get(username,"category");
    		String role = (String)template.opsForHash().get(username, "role");
    		user.setCategory(category);
    		user.setUsername(username);
    		user.setPassword(password);
    		user.setRole(role);
    		System.out.println("from redis");
    	}
        return user;
    }

    public User SaveUser(String username,String password,String category){
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setRole("customer");
        user.setCategory(category);
        userRepo.saveAndFlush(user);
        user = userRepo.getUserByUserName(username,password);
        return user;
    }

    public User getById(){
        return userRepo.getUserById();
    }
    
    public User getUserByUsername(String username) {
    	return userRepo.getUserByName(username);
    }
}