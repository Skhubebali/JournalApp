package com.jounalapp.journalApp.controller;

import com.jounalapp.journalApp.entity.JournalEntity;
import com.jounalapp.journalApp.entity.User;
import com.jounalapp.journalApp.service.JournalService;
import com.jounalapp.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService jservice;

    @Autowired
    private UserService userv;

    @PostMapping("{uname}")
    public ResponseEntity<?> store(@RequestBody JournalEntity je,@PathVariable String uname ) {
        je.setDate(LocalDate.now());
        jservice.createEntry(je,uname);
        return new ResponseEntity<JournalEntity>(HttpStatus.CREATED);
    }

    @GetMapping("{uname}")
    public ResponseEntity<?> getallJournalofUsers(@PathVariable String uname) {
        User  user = userv.findByUname(uname);
       List<JournalEntity> entries = user.getJentries();
       if(entries != null && !entries.isEmpty()){
           return new ResponseEntity<>(entries,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myid}")
    public Optional<JournalEntity> getbyId(@PathVariable String myid) {
        return jservice.showbyid(myid);
    }

    @PutMapping("{userid}/{myid}")
    public JournalEntity update(@PathVariable String myid,
                                @RequestBody JournalEntity je,@PathVariable String userid) {
        User user = userv.findByUname(userid);

        JournalEntity old = jservice.showbyid(myid).orElse(null);

        System.out.println("Old = " + old);

        if (old != null) {
            if (je.getTitle() != null) {
                old.setTitle(je.getTitle());
            }

            if (je.getContent() != null) {
                old.setContent(je.getContent());
            }

            return jservice.update(old);
        }

        return null;
    }
    @DeleteMapping("{username}/{myid}")
    public ResponseEntity<?> delete(@PathVariable String myid, @PathVariable String username) {
        jservice.delete(myid,username);
        return new ResponseEntity<JournalEntity>(HttpStatus.NO_CONTENT);
    }

}

