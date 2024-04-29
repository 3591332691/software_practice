package com.example.backend.service;

import com.example.backend.Entity.Book;
import com.example.backend.mapper.BookMapper;
import com.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookMapper bookMapper ;

    @Autowired
    UserMapper userMapper ;

    public List<Book> getAllBooks() {
        return bookMapper.getAllBooks();
    }
    public Book getBookById(int id) {
        return bookMapper.findBookById(id);
    }

    public List<Book> getBooksByAuthor(int author_id) throws  Exception{
        return bookMapper.findBookByAuthor(author_id);
    }

    public boolean addBook(Book book) throws Exception{
        int author_id = book.getAuthor_id();
        // 判断author是否存在
        if(userMapper.findUserById(author_id) == null)
            return false;
        bookMapper.insertBook(book);
        return true;
    }

    public boolean deleteBook(int id) throws Exception{
        // 判断是否存在该书
        if(bookMapper.findBookById(id) == null) return false;
        bookMapper.deleteBook(id);
        return true;
    }

    public void updateBook(Book book) throws Exception{
        bookMapper.updateBook(book);
    }

    public List<Book> searchBooksByName(String name) throws Exception{
        return bookMapper.findBookByName(name);
    }

    public List<Book> searchBooksByTag(String tag) throws Exception {
        return bookMapper.findBookByTag(tag);
    }
}
