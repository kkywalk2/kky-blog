package com.example.blog.repository;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoyCustom {
    Page<GetPostsData> findAllData(Pageable pageable);
    List<PostCategories> findCategoryCounts();
    Page<GetPostsData> findAllByCategory(String category, Pageable pageable);
    Page<GetPostsData> findByTitle(String title, Pageable pageable);
}
