package com.jounalapp.journalApp.controller;

import com.jounalapp.journalApp.entity.User;

import com.jounalapp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   public UserService userv;



    @GetMapping
    public User getUser(){
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userv.getByuname(username);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody User user ){
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User isuser= userv.findByUname(username);

           isuser.setUname(user.getUname());
           isuser.setPassword(user.getPassword());
           userv.createUser(isuser);

        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


}
