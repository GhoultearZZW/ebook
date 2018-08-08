package com.zzw.ebook.controller;

import com.zzw.ebook.model.Node.Friends;
import com.zzw.ebook.repository.Friends.AddFriends;
import com.zzw.ebook.repository.Friends.FriendsRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FriendsController {
    @Autowired
    FriendsRepository friendsRepository;

    private static String username;

    @PostMapping("/get/{username}")
    public ResponseEntity<Friends> get(@PathVariable("username")String name){
        Friends user = friendsRepository.findByName(name);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<Void> Create(@RequestBody Friends coder) throws Exception {

        Friends user = friendsRepository.save(coder);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/createRelation")
    @Transactional
    public ResponseEntity<Void> createRelation(@RequestBody JSONObject obj){
        String name1 = (String)obj.get("start");
        String name2 = (String)obj.get("end");

        friendsRepository.createRelation(name1,name2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/friends/{username}")
    @Transactional
    public String getFriends(@PathVariable("username")String name,Model model){
        List<Friends> list = friendsRepository.getFriends(name);
        model.addAttribute("list",list);
        model.addAttribute("username",name);
        System.out.println(list.size());
        model.addAttribute("AddFriends",new AddFriends());
        username = name;
        return "friends";
    }

    @PostMapping("/friends/add")
    @Transactional
    public String AddFriends(@ModelAttribute AddFriends addFriends,Model model){
        String end = addFriends.getEnd();
        if(friendsRepository.findByName(end)==null){
            Friends f = new Friends();
            f.setName(end);
            f.setUsername(end);
            friendsRepository.save(f);
        }
        friendsRepository.createRelation(username,end);
        System.out.println(username);
        List<Friends> list = friendsRepository.getFriends(username);
        model.addAttribute("list",list);
        model.addAttribute("username",username);
        model.addAttribute("AddFriends",new AddFriends());
        return "friends";
    }

}
