package com.jounalapp.journalApp.controller;

import com.jounalapp.journalApp.entity.User;
import com.jounalapp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    public UserService userv;

    @PostMapping("create-user")
    public ResponseEntity<User> create(@RequestBody User user){
        userv.createUser(user);
        return new ResponseEntity<User>(HttpStatus.CREATED);
    }
}
