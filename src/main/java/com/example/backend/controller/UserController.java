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
            return "添加用户成功";
        else return "用户已存在";
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
                return "用户不存在，无法更新";
            }
            user.setName(name);
            user.setImage(image);
            user.setNews(news);
            userService.updateUser(user);
        } catch (Exception e) {
            // 返回具体的错误信息给客户端
            return "更新用户失败：" + e.getMessage();
        }
        return "更新成功";

    }

    @GetMapping("/DeleteUser")
    public String DeleteUser(@RequestParam int open_id) throws Exception {
        boolean result = userService.deleteUser(open_id);
        if (result) return "删除成功";
        else {
            return "删除失败";
        }
    }

}
