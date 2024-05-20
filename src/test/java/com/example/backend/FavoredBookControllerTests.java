package com.example.backend;


import com.example.backend.Entity.Favored_book;
import com.example.backend.controller.FavoredBookController;
import com.example.backend.service.FavoredBookService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoredBookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoredBookService favoredBookService;

    @InjectMocks
    private FavoredBookController favoredBookController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFavoredBooksByReader0() throws Exception {
        List<Favored_book> favoredBooks = new ArrayList<>();
        // 添加测试数据
        Favored_book favoredBook1 = new Favored_book();
        favoredBook1.setReader_id(1);
        favoredBook1.setBook_id(1);
        favoredBooks.add(favoredBook1);

        Gson gson = new Gson();
        String favoredBooks_s =  gson.toJson(favoredBooks);

        when(favoredBookService.findFavoredBooksByReader(1)).thenReturn(favoredBooks);
        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/GetFavoredBooks/Reader")
                        .param("reader_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        String result = gson.toJson(favoredBooks);
        Assertions.assertEquals(result,mvcResult_s);
    }

    @Test
    public void testGetFavoredBooksByReader1() throws Exception {
        List<Favored_book> favoredBooks = new ArrayList<>();
        when(favoredBookService.findFavoredBooksByReader(1)).thenReturn(favoredBooks);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/GetFavoredBooks/Reader")
                        .param("reader_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("no favored books",mvcResult_s);
    }

    /**
     * 用来测试把正确的书加入书架
     * @throws Exception
     */
    @Test
    public void testAddFavoredBook0() throws Exception {
        String expectedResponse = "";
        //模拟输入，返回true
        when(favoredBookService.insertFavoredBook(any(Favored_book.class))).thenReturn(true);
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/AddFavoredBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reader_id\": 1, \"book_id\": 1, \"begin_time\": \"2024-04-30T22:32:32Z\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // 通过断言来验证返回是否正确
        Assertions.assertEquals("add succeed",content );
    }

    /**
     * 测试把不存在的书加入书架
     * @throws Exception
     */
    @Test
    public void testAddFavoredBook1() throws Exception {
        String expectedResponse = "";
        when(favoredBookService.insertFavoredBook(any(Favored_book.class))).thenReturn(false);
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/AddFavoredBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reader_id\": 1, \"book_id\": 1, \"begin_time\": \"2024-04-30T22:32:32Z\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // 通过断言来验证返回是否正确
        Assertions.assertEquals("add false",content );
    }

    @Test
    public void testDeleteFavoredBook0() throws Exception {
        String expectedResponse = "";
        when(favoredBookService.deleteFavoredBook(1)).thenReturn(true);
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/DeleteFavoredBook")
                        .param("favored_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // 通过断言来验证返回是否正确
        Assertions.assertEquals("delete succeed",content );
    }

    @Test
    public void testDeleteFavoredBook1() throws Exception {
        String expectedResponse = "";
        when(favoredBookService.deleteFavoredBook(1)).thenReturn(false);
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/DeleteFavoredBook")
                        .param("favored_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // 通过断言来验证返回是否正确
        Assertions.assertEquals("delete false",content );
    }

}
