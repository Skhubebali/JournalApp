package com.jounalapp.journalApp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Document(collection = "journal_entries")
@Getter
@Setter
@NoArgsConstructor
public class JournalEntity {
    @Id
    private String id;

    public String title;

    public String content;

    public LocalDate date;
}
