package com.example.backend;


import com.example.backend.Entity.Book;
import com.example.backend.Entity.User;
import com.example.backend.controller.SearchController;
import com.example.backend.service.BookService;
import com.example.backend.service.UserService;
import com.google.gson.Gson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @InjectMocks
    private SearchController searchController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchBookByName0() throws Exception {
        List<Book> books = new ArrayList<>();
        // 添加测试数据
        Book book1 = new Book();
        book1.setBook_id(1);
        book1.setBook_name("Book 1");
        books.add(book1);

        when(bookService.searchBooksByName("Book 1")).thenReturn(books);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/SearchBookByName")
                        .param("name", "Book 1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();
        String result = gson.toJson(books);
        Assertions.assertEquals(result,mvcResult_s);
    }

    @Test
    public void testSearchBookByName1() throws Exception {
        List<Book> books = new ArrayList<>();
        //设置返回为空
        when(bookService.searchBooksByName("Book 1")).thenReturn(books);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/SearchBookByName")
                        .param("name", "Book 1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals("no books found",mvcResult_s);
    }

    @Test
    public void testSearchBookByTag0() throws Exception {
        List<Book> books = new ArrayList<>();
        // 添加测试数据
        Book book1 = new Book();
        book1.setBook_id(1);
        book1.setBook_name("Book 1");
        books.add(book1);

        when(bookService.searchBooksByTag("tag")).thenReturn(books);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/SearchBookByTag")
                        .param("tag", "tag"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();
        String result = gson.toJson(books);
        Assertions.assertEquals(result,mvcResult_s);
    }

    @Test
    public void testSearchBookByTag1() throws Exception {
        List<Book> books = new ArrayList<>();
        when(bookService.searchBooksByTag("tag")).thenReturn(books);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/SearchBookByTag")
                        .param("tag", "tag"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("no books found",mvcResult_s);
    }

    @Test
    public void testSearchUserByName0() throws Exception {
        List<User> users = new ArrayList<>();
        // 添加测试数据
        User user1 = new User();
        user1.setOpen_id(1);
        user1.setName("User 1");
        users.add(user1);

        when(userService.findUserByName("User 1")).thenReturn(users);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/SearchUserByName")
                        .param("name", "User 1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();
        String result = gson.toJson(users);
        Assertions.assertEquals(result,mvcResult_s);

    }


    @Test
    public void testSearchUserByName1() throws Exception {
        List<User> users = new ArrayList<>();
        when(userService.findUserByName("User 1")).thenReturn(users);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/SearchUserByName")
                        .param("name", "User 1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("no users found",mvcResult_s);

    }
}
