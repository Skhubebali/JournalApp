package com.jounalapp.journalApp.controller;

import com.jounalapp.journalApp.entity.JournalEntity;
import com.jounalapp.journalApp.entity.User;
import com.jounalapp.journalApp.service.JournalService;
import com.jounalapp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService jservice;

    @Autowired
    private UserService userv;

    @PostMapping
    public ResponseEntity<?> store(@RequestBody JournalEntity je ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        je.setDate(LocalDate.now());
        jservice.createEntry(je);
        return new ResponseEntity<JournalEntity>(HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> getall(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String uname = auth.getName();
        User user = userv.findByUname(uname);
        List<JournalEntity> entries= user.getJentries();
        if(!entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("myid/{id}")
    public ResponseEntity<?> getbyId(@PathVariable String id) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       String uname = auth.getName();
       User user = userv.findByUname(uname);
       List<JournalEntity> collect= user.getJentries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
       if(!collect.isEmpty()){
           Optional<JournalEntity> jentry = jservice.showbyid(id);
           if(jentry.isPresent()){
               return new ResponseEntity<>(jentry,HttpStatus.FOUND);
           }
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PutMapping("myid/{myid}")
    public ResponseEntity<?> update(@RequestBody JournalEntity je,@PathVariable String myid) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String uname = auth.getName();
            User user = userv.findByUname(uname);
           JournalEntity old =  user.getJentries().stream().filter(x -> x.getId().equals(myid)).findFirst().orElse(null);
           if(old != null){
                if(je.getTitle() != null){
                    old.setTitle(je.getTitle());
                }
                if(je.getContent() != null){
                    old.setContent(je.getContent());
                }
               jservice.update(old);
               return new ResponseEntity<>(old,HttpStatus.OK);
           }

           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



    @DeleteMapping("{username}/{myid}")
    public ResponseEntity<?> delete(@PathVariable String myid, @PathVariable String username) {
        jservice.delete(myid,username);
        return new ResponseEntity<JournalEntity>(HttpStatus.NO_CONTENT);
    }

}

