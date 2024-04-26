package com.example.backend.controller;

import com.example.backend.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogInController {

    @Autowired
    private LogInService loginService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // 调用 LoginService 中的 authenticate 方法进行登录验证
        boolean isAuthenticated = loginService.authenticate(username, password);

        if (isAuthenticated) {
            return "Login successful!";
        } else {
            return "Invalid username";
        }
    }

    // 定义一个内部类来接收登录请求的数据
    static class LoginRequest {
        private String username;
        private String password;

        // getter 和 setter 方法
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}