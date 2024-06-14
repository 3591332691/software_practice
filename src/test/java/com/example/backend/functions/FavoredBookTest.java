package com.example.backend.functions;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoredBookTest {
    @Autowired
    private MockMvc mockMvc;

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        FavoredBookTest.jdbcTemplate = jdbcTemplate;
    }

    @BeforeAll
    static void setUpBeforeClass(@Autowired JdbcTemplate jdbcTemplate) {
        FavoredBookTest.jdbcTemplate = jdbcTemplate;
        // 导入shelfTest.sql数据
        executeSqlFile("/shelfTest.sql");
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
        check(shelfId, "Jun 10, 2024, 10:00:00 AM", "Jun 10, 2024, 10:00:00 AM", 1, 0);

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

        check(shelfId, "Jun 10, 2024, 10:00:00 AM", "Jun 10, 2024, 11:00:00 AM", 50, 60);
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
        check(shelfId, "Jun 11, 2024, 11:00:00 AM", "Jun 11, 2024, 11:00:00 AM", 50, 60);
    }

    // 模拟前端触发器
    @Test
    @Order(4)
    public void testAutoExitAndEnter() throws Exception {
        // 模拟用户阅读
        int shelfId = 1;
        int time_interval = 5;  // 时间间隔，单位分钟

        // 创建一个固定的时钟，初始时间为 "2024-06-11T12:00:00Z"
        Clock fixedClock = Clock.fixed(Instant.parse("2024-06-11T12:00:00Z"), ZoneId.of("GMT+8"));

        // 在测试中，通过调用以下方法来获取当前时间
        String now0 = fixedClock.instant().toString();

        // 发送进入信息
        Map<String, Object> enterReadingData = new HashMap<>();
        enterReadingData.put("shelf_id", shelfId);
        enterReadingData.put("begin_time", now0);

        mockMvc.perform(post("/UpdateFavoredBook/begin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(enterReadingData)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("update succeed")));

        // 模拟自动发送退出和进入(3次)
        for (int i = 0; i < 3; i++) {
            // 模拟等待
            fixedClock = Clock.offset(fixedClock, Duration.ofMinutes(time_interval));
            Instant current_time = fixedClock.instant();
            String now = current_time.toString();
            // 发送退出信息
            Map<String, Object> exitReadingData = new HashMap<>();
            exitReadingData.put("shelf_id", shelfId);
            exitReadingData.put("end_time", now);
            exitReadingData.put("reading_progress", 50 * (i + 1));

            mockMvc.perform(post("/UpdateFavoredBook/end")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(exitReadingData)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("update succeed")));

            // 发送进入信息
            enterReadingData.put("begin_time", now);

            mockMvc.perform(post("/UpdateFavoredBook/begin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enterReadingData)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("update succeed")));

            check(shelfId, dateTimeTransfer(current_time), dateTimeTransfer(current_time),
                    50 * (i + 1), 60 + time_interval * (i + 1));
        }

        Instant now_end = fixedClock.instant();
        String formattedDateTime = dateTimeTransfer(now_end);

        int reading_time = 60 + time_interval * 3;
        // 检查最终结果
        int shelfBookId = 1;
        mockMvc.perform(get("/GetFavoredBook/shelf")
                        .param("shelf_id", String.valueOf(shelfBookId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.end_time", is(formattedDateTime)))
                .andExpect(jsonPath("$.reading_progress", is(150)))
                .andExpect(jsonPath("$.reading_time", is(reading_time)));
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

    private static String dateTimeTransfer(Instant time) {
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy, hh:mm:ss a", Locale.ENGLISH);
        String DateTime = targetFormatter.format(time.atZone(ZoneId.of("UTC")));
        return DateTime.replace(" PM", " PM");
    }

    public static void executeSqlFile(String filePath) {
        try {
            InputStream inputStream = FavoredBookTest.class.getResourceAsStream(filePath);
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

    private void check(int shelf_id,String begin_time, String end_time, int reading_progress, int reading_time) throws Exception {
        mockMvc.perform(get("/GetFavoredBook/shelf")
                        .param("shelf_id", String.valueOf(shelf_id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.begin_time", equalTo(begin_time)))
                .andExpect(jsonPath("$.end_time", is(end_time)))
                .andExpect(jsonPath("$.reading_progress", is(reading_progress)))
                .andExpect(jsonPath("$.reading_time", is(reading_time)));
    }

}

