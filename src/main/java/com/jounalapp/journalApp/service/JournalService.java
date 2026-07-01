package com.jounalapp.journalApp.service;

import com.jounalapp.journalApp.entity.JournalEntity;
import com.jounalapp.journalApp.entity.User;
import com.jounalapp.journalApp.repo.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    public JournalRepo journalrepo;

    @Autowired
    public UserService userv;

    @Transactional
    public void createEntry(JournalEntity jentity ){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String uname = auth.getName();
            User user = userv.findByUname(uname);
            JournalEntity save = journalrepo.save(jentity);
            user.getJentries().add(save);
            userv.saveUser(user);
        }
        catch(Exception e){

            throw e;
        }
    }

    public List<JournalEntity> showall(){
        return journalrepo.findAll();
    }

    public Optional<JournalEntity> showbyid(String id){

        return journalrepo.findById(id);
    }

    public User findByname(String uname){
        return userv.findByUname(uname);
    }

    public void delete(String id, String username){
        User user = userv.findByUname(username);
        user.getJentries().removeIf(x -> x.getId().equals(id));
        userv.saveUser(user);
        journalrepo.deleteById(id);


    }

    public JournalEntity update(JournalEntity jentity){
        return journalrepo.save(jentity);
    }







}
