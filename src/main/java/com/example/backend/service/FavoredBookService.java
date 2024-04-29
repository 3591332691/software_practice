package com.example.backend.service;

import com.example.backend.Entity.Favored_book;
import com.example.backend.mapper.BookMapper;
import com.example.backend.mapper.Favored_bookMapper;
import com.example.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavoredBookService {
    @Autowired
    private Favored_bookMapper favored_bookMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    public Favored_book findById(int id) {
        return favored_bookMapper.findFavored_bookById(id);
    }
    public List<Favored_book> findFavoredBooksByReader(int reader_id) throws Exception {
        return favored_bookMapper.findFavored_bookByReader_id(reader_id);
    }

    public List<Favored_book> findFavoredBooksByBook(int book_id) throws Exception {
        return favored_bookMapper.findFavored_bookByBook_id(book_id);
    }

    public List<Favored_book> findFavoredBookByBookAndReader(int reader_id, int book_id) throws Exception {
        return favored_bookMapper.findFavored_bookByReader_idAndBook_id(reader_id, book_id);
    }

    public boolean insertFavoredBook(Favored_book favored_book) throws Exception {
        int reader_id = favored_book.getReader_id();
        int book_id = favored_book.getBook_id();

        if(userMapper.findUserById(reader_id) == null) return false; // 用户不存在
        if(bookMapper.findBookById(book_id) == null) return false; // 书籍不存在
        if(!findFavoredBookByBookAndReader(reader_id, book_id).isEmpty())
            return false; // 已加入书架，不可重复添加

        favored_bookMapper.insertBookIntoFavored_book(favored_book);
        return true;
    }

    public void updateFavored_book(Favored_book favored_book) throws Exception {
        favored_bookMapper.updateFavored_book(favored_book);
    }

    public boolean deleteFavoredBook(int shelf_id) throws Exception {
        // 找不到对应书架
        if(favored_bookMapper.findFavored_bookById(shelf_id) == null) return false;
        favored_bookMapper.deleteFavored_book(shelf_id);
        return true;
    }
}
