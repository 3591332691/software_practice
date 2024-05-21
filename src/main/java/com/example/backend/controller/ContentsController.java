package com.example.backend.controller;

import com.example.backend.Entity.Contents;
import com.example.backend.mapper.BookMapper;
import com.example.backend.mapper.ContentMapper;
import com.example.backend.service.ContentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ContentsController {

    @Autowired
    ContentMapper contentMapper ;
    @Autowired
    ContentService contentService ;
    @Autowired
    private BookMapper bookMapper;

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

    /**
     * 发布新章节
     * @param book_id
     * @param textContent 章节内容
     * @return
     */
    @GetMapping("/AddNewChapter")
    public String AddNewChapter(@RequestParam int book_id, @RequestParam String title, @RequestParam String textContent) throws IOException {
        if(bookMapper.findBookById(book_id)==null) {
            return "Book not found";
        }
        Contents contents = new Contents();
        contents.setBook_id(book_id);
        contents.setTitle(title);
        //先找到这个章节是书里的第几章
        int content_index_inBook = 1;
        List<Contents> contents_from_book = List.of();
        contents_from_book = contentMapper.getContentByBook_id(book_id);
        if (contents_from_book!=null) {
            content_index_inBook  = contents_from_book.size()+1;
        }
        contents.setContent_index_inBook(content_index_inBook);
        //把txtContent转成url
        String url = contentService.textToUrl(textContent);
        contents.setChapter(url);
        //
        contentMapper.insertContent(contents);
        return "Add chapter successfully";
    }

    @GetMapping("/EditChapter")
    public String EditChapter(@RequestParam int book_id,@RequestParam int chapter_index, @RequestParam String title, @RequestParam String textContent) throws IOException {
        if(bookMapper.findBookById(book_id)==null) {return "Book not found";}
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", book_id);
        params.put("contentIndexInBook", chapter_index);
        Contents contents = contentMapper.getContentByBook_idAndIndex_id(params);
        if (contents == null) {
            return "chapter not found";
        }
        else{
            contents.setTitle(title);
            String url = contentService.textToUrl(textContent);
            contents.setChapter(url);
            contentMapper.updateContent(contents);
            return "Modify chapter successfully";
        }

    }

}
