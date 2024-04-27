package com.example.backend.controller;

import com.example.backend.Entity.Book;
import com.example.backend.Entity.Contents;
import com.example.backend.service.BookService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    private Gson gson;

    /**
     * 创建书籍
     * @param createBookData
     * @return
     */
    @PostMapping("/createBook")
    public String createBook(@RequestBody Map<String, Object> createBookData) throws Exception {
        //读取表单
        String book_name = (String) createBookData.get("book_name");
        String brief_introduction = (String) createBookData.get("brief_introduction");
        String tag = (String) createBookData.get("tag");
        int author_id = (int) createBookData.get("author_id");

        //进行创建
        Book book = new Book();
        book.setBook_name(book_name);
        book.setBrief_introduction(brief_introduction);
        book.setTag(tag);
        book.setAuthor_id(author_id);

        boolean success = bookService.addBook(book);
        if(success)
            return "创建成功！";
        else
            return "创建失败";
    }

    @GetMapping("/DeleteBook")
    public String deleteBook(@RequestParam int book_id) throws Exception {
        boolean success = bookService.deleteBook(book_id);
        if(success) return "删除成功";
        else return "删除失败";
    }

    @PostMapping("/UpdateBook")
    public String updateBook(@RequestBody Map<String, Object> BookData) throws Exception {
        String book_name = (String) BookData.get("book_name");
        String brief_introduction = (String) BookData.get("brief_introduction");
        String tag = (String) BookData.get("tag");
        int book_id = (int) BookData.get("book_id");

        Book book = new Book();
        book.setBook_id(book_id);
        book.setBook_name(book_name);
        book.setBrief_introduction(brief_introduction);
        book.setTag(tag);

        boolean success = bookService.updateBook(book);
        if(success)
            return "更新成功";
        else
            return "更新失败";
    }

    @GetMapping("/GetBooksICreated")
    public String GetBooksICreated(@RequestParam int open_id) throws Exception {
        List<Book> books = bookService.getBooksByAuthor(open_id);
        if(books.isEmpty()) return "no books found";
        Gson gson = new Gson();
        return gson.toJson(books);
    }
}
