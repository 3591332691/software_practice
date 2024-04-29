package com.example.backend.Entity;


import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class User {
    private int open_id;

    private String name;

    private String image;

    private String news;
}