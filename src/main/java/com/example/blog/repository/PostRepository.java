package com.example.blog.repository;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blog.entity.PostEntity;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("select new com.example.blog.dto.post.GetPostsData(post.id, post.title, post.category, post.views) from PostEntity post where post.accountId = :accountId")
    List<GetPostsData> findAllByAccountID(@Param("accountId") Long accountId);

    @Query("select new com.example.blog.dto.post.PostCategories(post.category, COUNT(post.category)) from PostEntity post where post.accountId = :accountId group by post.category")
    List<PostCategories> findCategoryCounts(@Param("accountId") Long accountId);

    @Query("select new com.example.blog.dto.post.GetPostsData(post.id, post.title, post.category, post.views) from PostEntity post where post.accountId = :accountId and post.category = :category")
    List<GetPostsData> findAllByAccountIDAndCategory(@Param("accountId") Long accountId, @Param("category") String category);
}