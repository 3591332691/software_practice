package com.example.backend.mapper;

import com.example.backend.Entity.Contents;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ContentMapper {
    List<Contents> getContentByBook_id(int book_id);
}
