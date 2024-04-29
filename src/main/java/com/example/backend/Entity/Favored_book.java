package com.example.backend.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class Favored_book {
    private int id;
    private int book_id;
    private int reading_time;
    private int  reading_progress;

    private Date begin_time;
    private Date end_time;
    private int reader_id;
}
