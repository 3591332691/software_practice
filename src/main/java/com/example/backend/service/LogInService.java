package com.example.backend.service;

// LoginService.java

import org.springframework.stereotype.Service;

@Service
public class LogInService {

    public boolean authenticate(String username, String password) {
        // 在这里实现你的登录验证逻辑，比如从数据库中查询用户信息进行匹配
        // 这里只是一个简单的示例，实际项目中需要根据实际情况进行完善
        if ("admin".equals(username) && "password".equals(password)) {
            return true;
        } else {
            return false;
        }
    }

}
