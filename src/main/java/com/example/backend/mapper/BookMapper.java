package com.example.backend.mapper;

import com.example.backend.Entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    Book findBookById(int book_id);
    List<Book> getAllBooks();
    List<Book> findBookByAuthor(int author_id);
    List<Book> findBookByTag(String tag);
    List<Book> findBookByName(String name);
    void insertBook(Book book);
    void deleteBook(int book_id);
    void updateBook(Book book);
}
