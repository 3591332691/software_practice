package com.example.backend.functions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeTrackingTest {
    @Autowired
    private MockMvc mockMvc;

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        TimeTrackingTest.jdbcTemplate = jdbcTemplate;
    }

    @BeforeAll
    static void setUpBeforeClass(@Autowired JdbcTemplate jdbcTemplate) throws Exception {
        TimeTrackingTest.jdbcTemplate = jdbcTemplate;
        // 导入shelfTest.sql数据
        executeSqlFile("/TimeLoggingTest.sql");
    }
    @Test
    @Order(1)
    public void testAddFavoredBook() throws Exception {
        int bookId = 1;
        int readerId = 1;
        Map<String, Object> addToFavoritesData = new HashMap<>();
        addToFavoritesData.put("reader_id", readerId);
        addToFavoritesData.put("book_id", bookId);
        addToFavoritesData.put("begin_time", "2024-06-10T9:00:00Z");
        mockMvc.perform(post("/AddFavoredBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addToFavoritesData)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("add succeed")));

        // 检查属性是否符合预期
        int shelfBookId = 1;
        mockMvc.perform(get("/GetFavoredBook/shelf").
                        param("shelf_id", String.valueOf(shelfBookId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reader_id", is(readerId)))
                .andExpect(jsonPath("$.book_id", is(bookId)))
                .andExpect(jsonPath("$.begin_time", equalTo("Jun 10, 2024, 9:00:00 AM")))
                .andExpect(jsonPath("$.end_time", equalTo("Jun 10, 2024, 9:00:00 AM")))
                .andExpect(jsonPath("$.reading_progress", is(1)))
                .andExpect(jsonPath("$.reading_time", is(0)));
    }

    @Test
    @Order(2)
    public void testEnterAndExit() throws Exception {
        // 进入阅读
        int shelfId = 1;
        Map<String, Object> enterReadingData = new HashMap<>();
        enterReadingData.put("shelf_id", shelfId);
        enterReadingData.put("begin_time", "2024-06-10T10:00:00Z");

        mockMvc.perform(post("/UpdateFavoredBook/begin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(enterReadingData)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("update succeed")));

        // 检查属性是否符合预期
        int shelfBookId = 1;
        mockMvc.perform(get("/GetFavoredBook/shelf").
                        param("shelf_id", String.valueOf(shelfBookId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.begin_time", equalTo("Jun 10, 2024, 10:00:00 AM")))
                .andExpect(jsonPath("$.end_time", equalTo("Jun 10, 2024, 10:00:00 AM")));

        // 退出阅读
        Map<String, Object> exitReadingData = new HashMap<>();
        exitReadingData.put("shelf_id", shelfId);
        exitReadingData.put("end_time", "2024-06-10T11:00:00Z");
        exitReadingData.put("reading_progress", 50);

        mockMvc.perform(post("/UpdateFavoredBook/end")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(exitReadingData)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("update succeed")));

        mockMvc.perform(get("/GetFavoredBook/shelf").
                        param("shelf_id", String.valueOf(shelfBookId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.end_time", is("Jun 10, 2024, 11:00:00 AM")))
                .andExpect(jsonPath("$.reading_progress", is(50)))
                .andExpect(jsonPath("$.reading_time", is(60)));
    }

    // 模拟前端异常关闭，未发送退出阅读的信息至后端
    @Test
    @Order(3)
    public void testWithNoExit() throws Exception {

        // 进入阅读
        int shelfId = 1;
        Map<String, Object> enterReadingData = new HashMap<>();
        enterReadingData.put("shelf_id", shelfId);
        enterReadingData.put("begin_time", "2024-06-11T10:00:00Z");

        mockMvc.perform(post("/UpdateFavoredBook/begin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(enterReadingData)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("update succeed")));

        // 没有正常退出，即不调用退出接口

        // 再次进入
        Map<String, Object> enterReadingData2 = new HashMap<>();
        enterReadingData2.put("shelf_id", shelfId);
        enterReadingData2.put("begin_time", "2024-06-11T11:00:00Z");

        mockMvc.perform(post("/UpdateFavoredBook/begin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(enterReadingData2)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("update succeed")));

        // 查看属性
        mockMvc.perform(get("/GetFavoredBook/shelf").
                        param("shelf_id", String.valueOf(shelfId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.begin_time", is("Jun 11, 2024, 11:00:00 AM")))
                .andExpect(jsonPath("$.end_time", is("Jun 11, 2024, 11:00:00 AM")))
                .andExpect(jsonPath("$.reading_progress", is(50)))
                .andExpect(jsonPath("$.reading_time", is(60)));
    }


    @AfterAll
    static void tearDown() {
        // 清理测试数据
        String[] tables = {"user", "book", "favored_book", "contents"};

        for (String table : tables) {
            String sql = "DELETE FROM " + table;
            jdbcTemplate.update(sql);
        }
    }

    // 辅助方法：将对象转换为JSON字符串
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeSqlFile(String filePath) {
        try {
            InputStream inputStream = FavoredBookTest.class.getResourceAsStream(filePath);
            //InputStream inputStream = FavoredBookTest.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String line;
                StringBuilder sqlStatement = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    // 忽略注释和空行
                    if (line.trim().isEmpty() || line.startsWith("--")) {
                        continue;
                    }
                    sqlStatement.append(line);
                    if (line.trim().endsWith(";")) {
                        // 执行语句
                        executeSql(sqlStatement.toString());
                        // 重置语句
                        sqlStatement.setLength(0);
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeSql(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
