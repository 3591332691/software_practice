package com.example.backend.controller;

import com.example.backend.Entity.Book;
import com.example.backend.Entity.Contents;
import com.example.backend.mapper.BookMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    BookMapper bookMapper ;
    @Autowired
    private Gson gson;

    /**
     * 创建书籍
     * @param createBookData
     * @return
     */
    @PostMapping("/createBook")
    public String createBook(@RequestBody Map<String, Object> createBookData) {
        //读取表单
        String book_name = (String) createBookData.get("book_name");
        String brief_introduction = (String) createBookData.get("brief_introduction");
        String tag = (String) createBookData.get("tag");
        int author_id = (int) createBookData.get("author_id");
        //首先得到未创建之前的book数量
        List<Book> temp_pre = bookMapper.findBookByAuthor(author_id);
        int count_pre = 0;
        if (temp_pre.size() > 0) {count_pre = temp_pre.size();}
        //进行创建
        Book book = new Book();
        book.book_name = book_name;
        book.brief_introduction = brief_introduction;
        book.tag = tag;
        book.author_id = author_id;
        bookMapper.insertBook(book);
        //再查询一遍
        List<Book> temp_after = bookMapper.findBookByAuthor(author_id);
        int count_after = 0;
        if (temp_after.size() > 0) {count_after = temp_after.size();}
        //要返回创建成功或者创建失败的话，要查询是否创建成功
        if(count_after == count_pre+1) {
            return "创建成功！";
        }
        return "创建失败";
    }

    @GetMapping("/GetBooksICreated")
    public String GetBooksICreated(@RequestParam int open_id) {
        List<Book> books = bookMapper.findBookByAuthor(open_id);
        Gson gson = new Gson();
        String json = gson.toJson(books);
        return json;
    }
}
