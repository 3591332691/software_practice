package com.example.backend.service;

import com.example.backend.Entity.Book;
import com.example.backend.Entity.User;
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

    public User getUserById(int id) throws Exception{
        return userMapper.findUserById(id);
    }

    public String getUserInfo(int open_id){
        User user = userMapper.findUserById(open_id);
        if(user == null)
            return null;
        class userinfo{
            private String Name; // 用户昵称
            private List<Book> user_Books; // 用户发布的书籍列表
            public String image;
        }
        userinfo output=new userinfo();
        output.Name = userMapper.findUserById(open_id).getName();
        output.user_Books = bookMapper.findBookByAuthor(open_id);
        output.image = userMapper.findUserById(open_id).getImage();
        Gson gson = new Gson();
        String json1 = gson.toJson(output.Name);
        String json2 = gson.toJson(output.user_Books);
        String json3 = gson.toJson(output.image);
        return json1+json3+json2;
    }

    public boolean addUser(User user) throws Exception{
        if(userMapper.findUserById(user.getOpen_id()) != null) return false;
        else {
            userMapper.insertUser(user);
            return true;
        }
    }
    public boolean deleteUser(int id) throws Exception{
        if(userMapper.findUserById(id) == null) return false;
        userMapper.deleteUser(id);
        return true;
    }
    public void updateUser(User user) throws Exception{
        userMapper.updateUser(user);
    }
    public List<User> findUserByName(String name) throws Exception{
        return userMapper.findUserByName(name);
    }

}
