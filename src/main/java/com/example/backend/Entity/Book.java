package com.example.backend.Entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Book {
    private int book_id;
    private String book_name;
    private String brief_introduction;
    private String tag;
    private int author_id;
}
