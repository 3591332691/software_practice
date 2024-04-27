package com.example.backend.mapper;

import com.example.backend.Entity.Favored_book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Favored_bookMapper {
    List<Favored_book> findFavored_bookByBook_id(int book_id);
    List<Favored_book> findFavored_bookByReader_id(int reader_id);
    List<Favored_book> findFavored_bookByReader_idAndBook_id(int reader_id, int book_id);
    void insertBookIntoFavored_book(Favored_book favored_book);
    void deleteFavored_book(int id);
}
