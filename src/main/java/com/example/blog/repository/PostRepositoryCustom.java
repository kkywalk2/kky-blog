package com.example.blog.repository;

import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    Page<PostDto> findAllData(Pageable pageable, Optional<String> title, Optional<String> category);
    List<CategoryDto> findCategoryCounts();
}
