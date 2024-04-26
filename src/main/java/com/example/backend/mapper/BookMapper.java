package com.example.backend.mapper;

import com.example.backend.Entity.Book;

import java.util.List;

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
