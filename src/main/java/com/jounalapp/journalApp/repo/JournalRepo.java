package com.jounalapp.journalApp.repo;

import com.jounalapp.journalApp.entity.JournalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalRepo extends MongoRepository<JournalEntity,String> {
}
