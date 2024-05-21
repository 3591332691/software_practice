package com.example.backend.controller;


import com.example.backend.Entity.User;
import com.example.backend.service.BookService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @GetMapping("/GetUserInfo")
    public String GetUserInfo(@RequestParam int open_id) {
        String output = userService.getUserInfo(open_id);
        return output;
    }

    @PostMapping("/AddUser")
    public String AddUser(@RequestBody Map<String, Object> UserData) throws Exception {
        int open_id = (Integer) UserData.get("open_id");
        String name = (String) UserData.get("name");
        String image = (String) UserData.get("image");

        User user = new User();
        user.setOpen_id(open_id);
        user.setName(name);
        user.setImage(image);
        user.setNews("");

        if(userService.addUser(user))
            return "Add user successfully";
        else return "Fail to add user";
    }
    /*
   "open_id":2,
   "name": "Mary",
   "image": "null"
     */
    @PostMapping("/UpdateUser")
    public String UpdateUser(@RequestBody Map<String, Object> UserData) throws Exception {
        int open_id = (Integer) UserData.get("open_id");
        String name = (String) UserData.get("name");
        String image = (String) UserData.get("image");
        String news = (String) UserData.get("news");

        try {
            User user = userService.getUserById(open_id);
            if (user == null) {
                return "Fail to update user";
            }
            user.setName(name);
            user.setImage(image);
            user.setNews(news);
            userService.updateUser(user);
        } catch (Exception e) {
            // 返回具体的错误信息给客户端
            return "Fail to update user:" + e.getMessage();
        }
        return "Update user successfully";

    }

    @GetMapping("/DeleteUser")
    public String DeleteUser(@RequestParam int open_id) throws Exception {
        boolean result = userService.deleteUser(open_id);
        if (result) return "Delete user successfully";
        else {
            return "Fail to delete user";
        }
    }

}
