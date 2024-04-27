package com.example.backend.service;

import com.example.backend.Entity.Favored_book;
import com.example.backend.mapper.Favored_bookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoredBookService {
    @Autowired
    private Favored_bookMapper favored_bookMapper;

    public List<Favored_book> findFavoredBooksByReader(int reader_id) throws Exception {
        return favored_bookMapper.findFavored_bookByReader_id(reader_id);
    }

    public List<Favored_book> findFavoredBooksByBook(int book_id) throws Exception {
        return favored_bookMapper.findFavored_bookByBook_id(book_id);
    }

    public boolean insertFavoredBook(int reader_id, int book_id) throws Exception {
        if(!favored_bookMapper.findFavored_bookByReader_idAndBook_id(reader_id, book_id).isEmpty())
            return false;
        Favored_book favored_book = new Favored_book();
        favored_book.setFavored_book_id(book_id);
        favored_book.setReader_id(reader_id);
        favored_bookMapper.insertBookIntoFavored_book(favored_book);
        return true;
    }
}
