package com.example.backend.mapper;

import com.example.backend.Entity.Contents;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentMapper {
    Contents getContentById(int content_id);
}
