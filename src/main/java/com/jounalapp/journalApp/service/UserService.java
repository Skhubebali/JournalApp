package com.jounalapp.journalApp.service;

import com.jounalapp.journalApp.entity.User;
import com.jounalapp.journalApp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    public UserRepo userepo;

    private static final PasswordEncoder pwdencoder = new BCryptPasswordEncoder();

    public User createUser(User user){
        user.setPassword(pwdencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        return userepo.save(user);
    }
    public User saveUser(User user){
        return userepo.save(user);
    }

    public List<User> getUsers(){
        return userepo.findAll();
    }

    public User getByuname(String id){

        return userepo.findByUname(id);
    }

    public User findByUname(String uname){
        return userepo.findByUname(uname);
    }
    public User update(User user){
        user.setPassword(pwdencoder.encode(user.getPassword()));
        return userepo.save(user);
    }
}
