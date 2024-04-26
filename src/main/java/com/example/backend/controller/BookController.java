package com.example.backend.controller;

import com.example.backend.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BookController {
    @Autowired
    BookMapper bookMapper ;
    /**
     * 从书本id得到相关的title内容 ,chapter id
     * @param book_id
     * @return
     */
    @PostMapping("/GetBookChapterTitle")
    public String GetBookChapterTitle(@RequestParam int book_id) {
        // 如果存在书本
        if (bookMapper.findBookById(book_id)!=null) {
            String output = "aa";//TODO:这里要调用Bookservice的函数
        } else {
            return "No chapters found for the given title.";
        }
        return null;
    }
}
