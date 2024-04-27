package com.example.backend.controller;

import com.example.backend.Entity.Favored_book;
import com.example.backend.service.FavoredBookService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FavoredBookController {
    @Autowired
    private FavoredBookService favoredBookService;

    @GetMapping("/GetFavoredBooksByReader")
    public String GetFavoredBooksByReader(@RequestParam int reader_id) throws Exception{
        List<Favored_book> favored_books = favoredBookService.findFavoredBooksByReader(reader_id);
        if(!favored_books.isEmpty()){ return "no favored books"; }
        Gson gson = new Gson();
        return gson.toJson(favored_books);
    }

    @GetMapping("/GetFavoredBooksByBook")
    public String GetFavoredBooksByBook(@RequestParam int book_id) throws Exception{
        List<Favored_book> favored_books = favoredBookService.findFavoredBooksByBook(book_id);
        if(!favored_books.isEmpty()){ return "no favored books";}
        Gson gson = new Gson();
        return gson.toJson(favored_books);
    }

    @GetMapping("/AddFavoredBook")
    public String AddFavoredBook(@RequestBody Map<String, Object> FavoredData) throws Exception{
        int reader_id = (Integer) FavoredData.get("reader_id");
        int book_id = (Integer) FavoredData.get("book_id");

        return "添加成功";

    }
}
