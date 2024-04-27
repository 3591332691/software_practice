package com.example.backend.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contents {
    private int content_id;
    public String chapter;
    private String title;
    private int book_id;
    public int content_index_inBook;
}
