package com.example.backend;

import com.example.backend.Entity.Book;
import com.example.backend.Entity.Contents;
import com.example.backend.controller.ContentsController;
import com.example.backend.mapper.BookMapper;
import com.example.backend.mapper.ContentMapper;
import com.example.backend.service.ContentService;
import com.google.gson.Gson;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ContentsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ContentService contentService;

    @Mock
    private ContentMapper contentMapper;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private ContentsController contentsController;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contentsController).build();
    }

    @Test
    public void testGetBookChapterTitle() throws Exception {
        int bookId = 1;
        List<Contents> expectedContents = new ArrayList<>();
        Contents chapter1 = new Contents();
        chapter1.setBook_id(bookId);
        chapter1.setTitle("Chapter 1");
        Contents chapter2 = new Contents();
        chapter2.setBook_id(bookId);
        chapter2.setTitle("Chapter 2");
        expectedContents.add(chapter1);
        expectedContents.add(chapter2);

        when(contentMapper.getContentByBook_id(bookId)).thenReturn(expectedContents);

        mockMvc.perform(MockMvcRequestBuilders.get("/GetBookChapterTitle")
                        .param("book_id", String.valueOf(bookId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(expectedContents)));
    }

    @Test
    public void testGetBookChapterTitle_EmptyOrInvalidBook() throws Exception {
        int bookId = 1;

        // 模拟没有章节的情况
        when(contentMapper.getContentByBook_id(bookId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/GetBookChapterTitle")
                        .param("book_id", String.valueOf(bookId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void testGetChapterContent() throws Exception {
        int bookId = 1;
        int chapterId = 2;
        String expectedContent = "Chapter content";

        when(contentService.GetChapterContent(bookId, chapterId)).thenReturn(expectedContent);

        mockMvc.perform(MockMvcRequestBuilders.get("/GetChapterContent")
                        .param("book_id", String.valueOf(bookId))
                        .param("chapter_id", String.valueOf(chapterId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testGetChapterContent_EmptyOrInvalidChapter() throws Exception {
        int bookId = 1;
        int chapterId = 2;

        when(contentService.GetChapterContent(bookId, chapterId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/GetChapterContent")
                        .param("book_id", String.valueOf(bookId))
                        .param("chapter_id", String.valueOf(chapterId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }



    @Test
    public void testAddNewChapter() throws Exception {
        int bookId = 1;
        String title = "New Chapter";
        String textContent = "Chapter content";
        String expectedResult = "Add chapter successfully";

        when(bookMapper.findBookById(bookId)).thenReturn(new Book());
        when(contentMapper.getContentByBook_id(bookId)).thenReturn(new ArrayList<>());
        when(contentService.textToUrl(textContent)).thenReturn("chapter-url");

        mockMvc.perform(MockMvcRequestBuilders.get("/AddNewChapter")
                        .param("book_id", String.valueOf(bookId))
                        .param("title", title)
                        .param("textContent", textContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResult));
    }

    @Test
    public void testAddNewChapter_InvalidBook() throws Exception {
        int bookId = 1;
        String title = "New Chapter";
        String textContent = "Chapter content";
        String expectedResult = "Book not found";

        // 模拟书籍不存在的情况
        when(bookMapper.findBookById(bookId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/AddNewChapter")
                        .param("book_id", String.valueOf(bookId))
                        .param("title", title)
                        .param("textContent", textContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResult));
    }

    @Test
    public void testEditChapter() throws Exception {
        int bookId = 1;
        int chapterIndex = 2;
        String title = "Updated Chapter";
        String textContent = "Updated chapter content";
        String expectedResult = "Modify chapter successfully";

        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("contentIndexInBook", chapterIndex);
        Contents existingContent = new Contents();
        existingContent.setBook_id(bookId);
        existingContent.setContent_id(chapterIndex);
        existingContent.setTitle("chapter title");

        when(bookMapper.findBookById(bookId)).thenReturn(new Book());
        when(contentMapper.getContentByBook_idAndIndex_id(params)).thenReturn(existingContent);
        when(contentService.textToUrl(textContent)).thenReturn("updated-chapter-url");

        mockMvc.perform(MockMvcRequestBuilders.get("/EditChapter")
                        .param("book_id", String.valueOf(bookId))
                        .param("chapter_index", String.valueOf(chapterIndex))
                        .param("title", title)
                        .param("textContent", textContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResult));
    }

    @Test
    public void testEditChapter_InvalidChapter() throws Exception {
        int bookId = 1;
        int chapterIndex = 2;
        String title = "Updated Chapter";
        String textContent = "Updated chapter content";
        String expectedResult = "chapter not found";

        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("contentIndexInBook", chapterIndex);

        when(bookMapper.findBookById(bookId)).thenReturn(new Book());
        when(contentMapper.getContentByBook_idAndIndex_id(params)).thenReturn(null);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/EditChapter")
                        .param("book_id", String.valueOf(bookId))
                        .param("chapter_index", String.valueOf(chapterIndex))
                        .param("title", title)
                        .param("textContent", textContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResult));
    }

}
