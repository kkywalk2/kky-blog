package com.example.blog.service;

import com.example.blog.dto.post.PostDto;
import com.example.blog.dto.post.CategoryDto;
import com.example.blog.repository.PostRepository;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Optional;

import com.example.blog.entity.PostEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Page<PostDto> getPosts(Pageable pageable, Optional<String> title, Optional<String> category) {
        return postRepository.findAllData(pageable, title, category);
    }

    //todo: modify return type to dto
    @Transactional(readOnly = true)
    public PostEntity getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Unavailable Post"));
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getCategoryCounts() {
        return postRepository.findCategoryCounts();
    }

    @Transactional
    public void updatePost(Long accountId, Long postId, String title, String content, String category) {
        PostEntity postEntity = postRepository.getById(postId);
        Preconditions.checkState(postEntity.getAccountId() == accountId, "Unauthorized");
        postEntity.setTitle(title);
        postEntity.setContent(content);
        postEntity.setCategory(category);
    }

    //TODO: modify return type to dto
    @Transactional
    public void deletePost(Long accountId, Long postId) {
        PostEntity postEntity = postRepository.getById(postId);
        Preconditions.checkState(postEntity.getAccountId() == accountId, "Unauthorized");
        postRepository.deleteById(postId);
    }
}
