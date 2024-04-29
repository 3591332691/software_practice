package com.example.backend.controller;

import com.example.backend.Entity.Book;
import com.example.backend.Entity.User;
import com.example.backend.service.BookService;
import com.example.backend.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @GetMapping("/SearchBookByName")
    public String searchBookByName(String name) throws Exception {
        List<Book> books = bookService.searchBooksByName(name);
        if (books.isEmpty()) {return "no books found";}
        Gson gson = new Gson();
        return gson.toJson(books);
    }

    @GetMapping("/SearchBookByTag")
    public String searchBookByTag(String tag) throws Exception {
        List<Book> books = bookService.searchBooksByTag(tag);
        if (books.isEmpty()) {return "no books found";}
        Gson gson = new Gson();
        return gson.toJson(books);
    }

    @GetMapping("/SearchUserByName")
    public String searchUserByName(String name) throws Exception {
        List<User> users = userService.findUserByName(name);
        if (users.isEmpty()) {return "no users found";}
        Gson gson = new Gson();
        return gson.toJson(users);
    }

}
