package com.example.backend.controller;

import com.example.backend.Entity.Contents;
import com.example.backend.mapper.ContentMapper;
import com.example.backend.service.ContentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContentsController {

    @Autowired
    ContentMapper contentMapper ;
    @Autowired
    ContentService contentService ;
    /**
     * 从书本id得到相关的content title内容 ,chapter id
     */
    @GetMapping("/GetBookChapterTitle")
    public String GetBookChapterTitle(@RequestParam int book_id) {
        List<Contents> output = List.of();
        // 如果存在书本
        if (contentMapper.getContentByBook_id(book_id)!=null) {
            output = contentMapper.getContentByBook_id(book_id);
        }
        Gson gson = new Gson();
        return gson.toJson(output);
    }

    /**
     * 得到书本id 和chapter_id，返回String章节内容
     * @param chapter_id
     * @return
     */
    @GetMapping("/GetChapterContent")
    public String GetChapterContent(@RequestParam int book_id, @RequestParam int chapter_id) {
        return contentService.GetChapterContent(book_id, chapter_id);
    }

}
