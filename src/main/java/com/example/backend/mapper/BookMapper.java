package com.example.backend.mapper;

import com.example.backend.Entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {
    Book findBookById(int id);
    List<Book> getAllBooks();
    List<Book> findBookByAuthor();
    List<Book> findBookByTag();
    List<Book> findBookByName(String name);
    void insertBook(Book book);
    void deleteBook(int book_id);
    void updateBook(Book book);
}
