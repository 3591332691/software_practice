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
