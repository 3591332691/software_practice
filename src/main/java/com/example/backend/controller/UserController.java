package com.example.backend.controller;

import com.example.backend.Entity.Book;
import com.example.backend.mapper.BookMapper;
import com.example.backend.service.BookService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BookMapper bookMapper;

    @GetMapping("/GetUserInfo")
    public String GetUserInfo(@RequestParam int open_id) {
        String output = userService.getUserInfo(open_id);
        return output;
    }


}
