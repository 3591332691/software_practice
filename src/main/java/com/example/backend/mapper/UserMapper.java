package com.example.backend.mapper;

import com.example.backend.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface UserMapper {
    // 根据用户ID查询用户信息
    User findUserById(int id);

    // 查询所有用户信息
    List<User> getAllUsers();

    // 模糊查找用户
    List<User> findUserByName(String name);

    // 插入用户信息
    void insertUser(User user);

    // 更新用户信息
    void updateUser(User user);

    // 删除用户信息
    void deleteUser(int id);
}
