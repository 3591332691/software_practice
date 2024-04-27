package com.example.backend.service;

import com.example.backend.Entity.Book;
import com.example.backend.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookMapper bookMapper ;

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
        //首先得到未创建之前的book数量
        List<Book> temp_pre = bookMapper.findBookByAuthor(author_id);
        int count_pre = 0;
        if (!temp_pre.isEmpty()) {count_pre = temp_pre.size();}

        bookMapper.insertBook(book);

        List<Book> temp_after = bookMapper.findBookByAuthor(author_id);
        int count_after = temp_after.size();
        //返回是否创建成功
        return count_after == count_pre + 1;

    }
    public boolean deleteBook(int id) throws Exception{
        // 判断是否存在该书
        if(bookMapper.findBookById(id) == null) return false;
        List<Book> temp_pre = bookMapper.findBookByAuthor(id);
        int count_pre = 0;
        if (!temp_pre.isEmpty()) {count_pre = temp_pre.size();}

        bookMapper.deleteBook(id);

        List<Book> temp_after = bookMapper.findBookByAuthor(id);
        int count_after = temp_after.size();
        return count_after == count_pre - 1;
    }

    public boolean updateBook(Book book) throws Exception{
        int book_id = book.getBook_id();
        // 查找书籍是否存在
        if(bookMapper.findBookById(book_id) == null) return false;
        bookMapper.updateBook(book);
        return true;
    }

    public List<Book> searchBooksByName(String name) throws Exception{
        return bookMapper.findBookByName(name);
    }

    public List<Book> searchBooksByTag(String tag) throws Exception {
        return bookMapper.findBookByTag(tag);
    }
}
