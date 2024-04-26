package com.example.backend.mapper;

import com.example.backend.Entity.Favored_book;

import java.util.List;

public interface Favored_bookMapper {
    List<Favored_book> findFavored_bookByBook_id(int book_id);
    List<Favored_book> findFavored_bookByReader_id(int reader_id);
    void insertBookIntoFavored_book(Favored_book favored_book);
    void deleteFavored_book(int id);
}
