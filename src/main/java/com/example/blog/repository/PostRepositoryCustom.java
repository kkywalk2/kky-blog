package com.example.blog.repository;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    Page<GetPostsData> findAllData(Pageable pageable, Optional<String> title, Optional<String> category);
    List<PostCategories> findCategoryCounts();
}
