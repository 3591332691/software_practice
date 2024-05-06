package com.example.backend.service;

import com.example.backend.Entity.Book;
import com.example.backend.Entity.User;
import com.example.backend.mapper.BookMapper;
import com.example.backend.mapper.UserMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BookMapper bookMapper;
    public String toJsonString(Object obj) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(java.lang.reflect.Modifier.STATIC)
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls() // 将 null 值序列化为 JSON
                .create();

        return gson.toJson(obj);
    }
    public User getUserById(int id) throws Exception{
        return userMapper.findUserById(id);
    }
    @Getter
    @Setter
    class userinfo{
        private String Name; // 用户昵称
        private List<Book> user_Books; // 用户发布的书籍列表
        public String image;
    }
    public String getUserInfo(int open_id){
        User user = userMapper.findUserById(open_id);
        if(user == null)
            return null;

        userinfo output=new userinfo();
        output.Name = user.getName();
        output.user_Books = bookMapper.findBookByAuthor(open_id);
        output.image = user.getImage();
        // 创建一个 GsonBuilder 实例
        GsonBuilder gsonBuilder = new GsonBuilder();

        // 调用 serializeNulls() 方法，使 Gson 序列化 null 值
        gsonBuilder.serializeNulls();

        // 使用 GsonBuilder 创建 Gson 实例
        Gson gson = gsonBuilder.create();
        // 将对象序列化为 JSON 字符串
        String jsonString = gson.toJson(output);
        return jsonString;
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
