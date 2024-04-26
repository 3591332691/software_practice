package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@SpringBootApplication
public class BackendApplication {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	/**
	 * 在这里初始化数据库
     */
	@Bean
	public CommandLineRunner init_database(JdbcTemplate jdbcTemplate) throws Exception {
		// 读取 SQL 文件内容
        executeSqlFile("lab1test.sql");
		return args -> {};
    }

	public void executeSqlFile(String filePath) {
		try {
			URL url = getClass().getClassLoader().getResource(filePath);
			System.out.println("Resource URL: " + url);
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
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

	private void executeSql(String sql) {
		try {
			jdbcTemplate.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
