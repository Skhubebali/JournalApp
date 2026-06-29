package com.jounalapp.journalApp.repo;

import com.jounalapp.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String> {

    User findByUname(String uname);
}
