package com.example.backend.service;

import com.example.backend.Entity.Book;
import com.example.backend.mapper.BookMapper;
import com.example.backend.mapper.UserMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BookMapper bookMapper;

    public String getUserInfo(int open_id){
        class userinfo{
            private String Name; // 用户昵称
            private List<Book> user_Books; // 用户发布的书籍列表
            public String image;
        }
        userinfo output=new userinfo();
        output.Name = userMapper.findUserById(open_id).name;
        output.user_Books = bookMapper.findBookByAuthor(open_id);
        output.image = userMapper.findUserById(open_id).image;
        Gson gson = new Gson();
        String json1 = gson.toJson(output.Name);
        String json2 = gson.toJson(output.user_Books);
        String json3 = gson.toJson(output.image);
        return json1+json3+json2;
    }
}
