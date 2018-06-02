package com.zzw.ebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zzw.ebook.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
/*
    @RequestMapping(value="/",method= RequestMethod.GET)
    public String index(){
        return "index";
    }*/
}
