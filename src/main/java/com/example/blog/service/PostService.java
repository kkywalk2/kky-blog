package com.example.blog.service;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;
import com.example.blog.repository.PostRepository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.blog.entity.PostEntity;

import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void createPost(Long accountId, String title, String content, String category) {
        postRepository.save(new PostEntity(accountId, title, content, category));
    }

    public List<GetPostsData> getPosts() {
        return postRepository.findAllDatas();
    }

    public List<GetPostsData> getPosts(String category) {
        return postRepository.findAllByCategory(category);
    }

    public PostEntity getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Unavailable Post"));
    }

    public List<PostCategories> getCategoryCounts() {
        return postRepository.findCategoryCounts();
    }
}
