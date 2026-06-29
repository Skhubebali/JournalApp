package com.jounalapp.journalApp.controller;

import com.jounalapp.journalApp.entity.User;

import com.jounalapp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   public UserService userv;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
       userv.createUser(user);
       return new ResponseEntity<User>(HttpStatus.CREATED);
    }
     @GetMapping
    public List<User> getUsers(){
       return  userv.getUsers();

    }
    @GetMapping("uname/{uname}")
    public User getUser(@PathVariable String uname){
        return userv.getByuname(uname);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> update(@RequestBody User user , @PathVariable String username){
        User isuser= userv.findByUname(username);
        if(isuser != null){
           isuser.setUname(user.getUname());
           isuser.setPassword(user.getPassword());
           userv.update(isuser);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


}
