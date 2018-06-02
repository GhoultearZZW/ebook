 package com.zzw.ebook.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzw.RmiServer.RmiClient;
import com.zzw.ebook.Kafka.KafkaSender;
import com.zzw.ebook.model.Book;
import com.zzw.ebook.model.Order;
import com.zzw.ebook.model.OrderItem;
import com.zzw.ebook.model.User;
import com.zzw.ebook.security.SampleCallbackHandler;
import com.zzw.ebook.security.WordCheckTextArea;
import com.zzw.ebook.service.BookService;
import com.zzw.ebook.service.OrderService;
import com.zzw.ebook.service.UserService;
import com.zzw.ebook.utils.SpringUtil;

import antlr.debug.GuessingEvent;



@Controller
public class HelloController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;
    @Autowired
    OrderService orderService;

    @PostMapping("/login")
    public String Login(@ModelAttribute User user,Model model) throws LoginException{
    	//test
    	User guest = userService.CheckUser(user.getUsername(),user.getPassword());
    	
    	SampleLoginModule sampleLoginModule = new SampleLoginModule();
    	SampleCallbackHandler sampleCallbackHandler = new SampleCallbackHandler(user.getUsername(), user.getPassword());
    	Subject subject = new Subject();
    	Map<String, String> options = new HashMap<>();
    	Map<String, String> sharedState = new HashMap<>();
    	sampleLoginModule.initialize(subject, sampleCallbackHandler, sharedState, options);
    	if(!sampleLoginModule.login()) {
    		return "login";
    	}
    	
        User Guest=userService.CheckUser(user.getUsername(),user.getPassword());
        if(Guest==null)
            return "login";
        model.addAttribute("user",Guest);
        model.addAttribute("username",user.getUsername());
        model.addAttribute("userId",user.getUserId());
        List<Book> bookList = bookService.getBooks();
        Iterator<Book> iter = bookList.iterator();
        WordCheckTextArea wordCheck = new WordCheckTextArea();
        List<Book> newList = new ArrayList<>();
        while(iter.hasNext()) {
        	Book book = iter.next();
        	if(!wordCheck.append(Guest.getCategory(),book.getCategory())) {
        		newList.add(book);
        	}
        }
        model.addAttribute("bookList",newList);
        return "home";
    }

    @GetMapping("/index")
    public String loginCheck(Model model){
        model.addAttribute("user",new User());
        WordCheckTextArea wordCheck = new WordCheckTextArea();
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "register";
    }


    @GetMapping("/buy/{username}/{title}")
    public String Buy(Model model,@PathVariable("title") String title,@PathVariable("username") String username) throws RemoteException, MalformedURLException, NotBoundException {
    	Book book = bookService.getBookByTitle(title);
    	int remaining = book.getRemaining();
    	model.addAttribute("user", userService.getUserByUsername(username));
    	if(remaining >= 1) {
    		bookService.MinusOnInRemaining(title);
    		Order order = new Order();
    		order.setDate(new Timestamp(new Date().getTime()));
    		order.setStatus(RmiClient.getResult());
    		System.out.println(order.getStatus());
    		OrderItem orderItem = new OrderItem();
    		orderItem.setAmount(1);
    		orderItem.setBook(book);
    		orderItem.setCustomer(userService.getUserByUsername(username));
    		orderItem.setPrice(book.getPrice());
    		orderItem.setOrder(order);
    		List<OrderItem> list = new ArrayList<>();
    		list.add(orderItem);
    		order.setOrderItems(list);
    		orderService.saveOrder(order);
    		orderService.saveOrderItem(orderItem);
    		return "buy";
    	}
    	else {
    		return "cantBuy";
    	}
    }
    
    @GetMapping("/getHome/{username}")
    public String getHome(Model model,@PathVariable("username")String username) {
    	model.addAttribute("username",username);
    	User Guest = userService.getUserByUsername(username);
        List<Book> bookList = bookService.getBooks();
        Iterator<Book> iter = bookList.iterator();
        WordCheckTextArea wordCheck = new WordCheckTextArea();
        List<Book> newList = new ArrayList<>();
        while(iter.hasNext()) {
        	Book book = iter.next();
        	if(!wordCheck.append(Guest.getCategory(),book.getCategory())) {
        		newList.add(book);
        	}
        	System.out.println(newList.size());
        }
        model.addAttribute("bookList",newList);
    	return "home";
    }
    /*
    @PostMapping("/register")
    public String Register(@ModelAttribute User user,Model model){
    	KafkaSender sender = new KafkaSender();
    	System.out.println("1111111111111111111");
    	sender.send(user.getUsername(), user.getPassword());
    	System.out.println("2222222222222222222");
        //User guest = userService.SaveUser(user.getUsername(),user.getPassword(),user.getCategory());
    	model.addAttribute("username",user.getUsername());
    	System.out.println("3333333333333333333");
        model.addAttribute("bookList",bookService.getBooks());
        return "home";
    }*/
}
