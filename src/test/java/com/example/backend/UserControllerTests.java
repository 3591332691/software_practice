package com.example.backend;

import com.example.backend.Entity.User;
import com.example.backend.controller.UserController;

import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserInfo() throws Exception {
        int openId = 1;
        String userInfo = "User information";
        when(userService.getUserInfo(openId)).thenReturn(userInfo);

        mockMvc.perform(MockMvcRequestBuilders.get("/GetUserInfo")
                        .param("open_id", String.valueOf(openId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(userInfo)); // 预期返回空字符串
    }

    @Test
    public void testInvalidGetUserInfo() throws Exception {
        int openId = 1;

        when(userService.getUserInfo(openId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/GetUserInfo")
                        .param("open_id", String.valueOf(openId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void testAddUser() throws Exception {

        User user = new User();
        user.setOpen_id(2);
        user.setName("Mary");
        user.setImage("null");
        user.setNews("");

        when(userService.addUser(any(User.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/AddUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"open_id\": 2, \"name\": \"Mary\", \"image\": \"null\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Add user successfully"));
    }

    @Test
    public void testInvalidAddUser() throws Exception {

        User user = new User();
        user.setOpen_id(2);
        user.setName("Mary");
        user.setImage("null");
        user.setNews("");

        when(userService.addUser(any(User.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/AddUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"open_id\": 2, \"name\": \"Mary\", \"image\": \"null\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Fail to add user"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setOpen_id(3);

        when(userService.getUserById(3)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/UpdateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"open_id\": 3, \"name\": \"John\", \"image\": \"null\", \"news\": \"news\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Update user successfully"));
    }

    @Test
    public void testInvalidUpdateUser() throws Exception {
        User user = new User();
        user.setOpen_id(3);

        when(userService.getUserById(3)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/UpdateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"open_id\": 3, \"name\": \"John\", \"image\": \"null\", \"news\": \"news\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Fail to update user"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        int openId = 4;
        when(userService.deleteUser(openId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/DeleteUser")
                        .param("open_id", String.valueOf(openId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Delete user successfully"));
    }

    @Test
    public void testInvalidDeleteUser() throws Exception {
        int openId = 4;
        when(userService.deleteUser(openId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/DeleteUser")
                        .param("open_id", String.valueOf(openId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Fail to delete user"));
    }
}