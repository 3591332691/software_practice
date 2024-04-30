package com.example.backend.mapper;

import com.example.backend.Entity.Contents;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentMapper {
    List<Contents> getContentByBook_id(int book_id);
    void insertContent(Contents contents);
    Contents getContentByBook_idAndIndex_id(int book_id, int content_index_inBook);
    void updateContent(Contents contents);
}
