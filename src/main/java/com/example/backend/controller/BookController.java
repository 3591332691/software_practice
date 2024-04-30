package com.example.backend.controller;

import com.example.backend.Entity.Book;
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
    Gson gson;

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
        String image = (String) createBookData.get("image");
        int author_id = (int) createBookData.get("author_id");


        //进行创建
        Book book = new Book();
        book.setBook_name(book_name);
        book.setBrief_introduction(brief_introduction);
        book.setTag(tag);
        book.setImage(image);
        book.setAuthor_id(author_id);

        boolean success = bookService.addBook(book);
        if(success)
            return "创建成功！";
        else
            return "创建失败";
    }
    /*
    "book_name": "软件测试",
     "brief_introduction":"软工必修课程",
     "tag":"学习",
     "image": "null",
     "author_id":1
     */
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
        String image = (String) BookData.get("image");
        int book_id = (int) BookData.get("book_id");
        try{
            Book book = bookService.getBookById(book_id);
            if(book == null) return "书籍不存在，更新失败";
            book.setBook_name(book_name);
            book.setBrief_introduction(brief_introduction);
            book.setTag(tag);
            book.setImage(image);
            bookService.updateBook(book);
        } catch (Exception e){
            // 返回具体的错误信息给客户端
            return "更新书籍失败：" + e.getMessage();
        }
        return "更新书籍成功";
    }

    @GetMapping("/GetBooksICreated")
    public String GetBooksICreated(@RequestParam int open_id) throws Exception {
        List<Book> books = bookService.getBooksByAuthor(open_id);
        if(books.isEmpty()) return "no books found";
        Gson gson = new Gson();
        return gson.toJson(books);
    }

    // 获取某本书的名字、简介、标签、作者id等详细信息
    @GetMapping("/GetBookDetail")
    public String GetBookDetail(@RequestParam int book_id) throws Exception {
        Book book = bookService.getBookById(book_id);
        if(book == null) return "no book found";
        else return gson.toJson(book);
    }

    // 测试用接口，实际应用估计不太会使用
    @GetMapping("/GetAllBooks")
    public String GetAllBooks() throws Exception {
        List<Book> books = bookService.getAllBooks();
        if(books.isEmpty()) return "no books found";
        Gson gson = new Gson();
        return gson.toJson(books);
    }

    @GetMapping("/BookPublish")
    public String BookPublish(@RequestParam int book_id) throws Exception {
        if(bookService.getBookById(book_id) == null) return "no book found";
        else {//如果有这本书
            Book book = bookService.getBookById(book_id);
            //更新book的上架属性
            book.setPublish(true);
            bookService.updateBook(book);
            return "上架成功";
        }
    }
    @GetMapping("/BookPublishRemove")
    public String BookPublishRemove(@RequestParam int book_id) throws Exception {
        if(bookService.getBookById(book_id) == null) return "no book found";
        else {//如果有这本书
            Book book = bookService.getBookById(book_id);
            //更新book的上架属性
            book.setPublish(false);
            bookService.updateBook(book);
            return "下架成功";
        }
    }
    @GetMapping("/EditBookInfo")
    public String EditBookInfo(@RequestParam int book_id,@RequestParam String book_name,@RequestParam String brief_introduction,@RequestParam String tag) throws Exception {
        Book book = bookService.getBookById(book_id);
        if(book == null) return "no book found";
        book.setBook_name(book_name);
        book.setBrief_introduction(brief_introduction);
        book.setTag(tag);
        bookService.updateBook(book);
        return "更新书籍信息成功";
    }
}
