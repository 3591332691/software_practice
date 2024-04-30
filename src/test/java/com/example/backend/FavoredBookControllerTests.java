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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoredBookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FavoredBookService favoredBookService;

    @InjectMocks
    private FavoredBookController favoredBookController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFavoredBooksByReader() throws Exception {
        List<Favored_book> favoredBooks = new ArrayList<>();
        // 添加测试数据
        Favored_book favoredBook1 = new Favored_book();
        favoredBook1.setReader_id(1);
        favoredBook1.setBook_id(1);
        favoredBooks.add(favoredBook1);

        when(favoredBookService.findFavoredBooksByReader(1)).thenReturn(favoredBooks);

        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/GetFavoredBooks/Reader")
                        .param("reader_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String mvcResult_s = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();
        String result = gson.toJson(favoredBooks);
        Assertions.assertEquals(result,mvcResult_s);
    }

    @Test
    public void testAddFavoredBook() throws Exception {
        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/AddFavoredBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reader_id\": 1, \"book_id\": 1, \"begin_time\": \"2024-04-30T22:32:32Z\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // 通过断言来验证返回是否正确
        Assertions.assertEquals("add succeed",content );
    }

    @Test
    public void testDeleteFavoredBook() throws Exception {
        String expectedResponse = "";
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.get("/DeleteFavoredBook")
                        .param("favored_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // 通过断言来验证返回是否正确
        Assertions.assertEquals("delete false",content );
    }

}
