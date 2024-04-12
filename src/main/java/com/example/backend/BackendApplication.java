package com.example.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/**
	 * 在这里初始化数据库
     */
	@Bean
	public CommandLineRunner initData(JdbcTemplate jdbcTemplate) {
		return args -> {
			//TODO:朋友，你就在这里写数据库的初始化就行
			//如果没有User表，就创建一个User表 TODO：后面要改成微信授权信息（可能是微信号？）
			jdbcTemplate.execute("DROP TABLE IF EXISTS User");
			jdbcTemplate.execute("CREATE TABLE User (UserId INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL)");

		};
	}
}
