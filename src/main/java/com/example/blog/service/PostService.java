package com.example.blog.service;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.PostRepositorySupport;
import com.google.common.base.Preconditions;

import java.util.List;

import javax.transaction.Transactional;

import com.example.blog.entity.PostEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostRepositorySupport postRepositorySupport;

    PostService(PostRepository postRepository, PostRepositorySupport postRepositorySupport) {
        this.postRepository = postRepository;
        this.postRepositorySupport = postRepositorySupport;
    }

    @Transactional
    public void createPost(Long accountId, String title, String content, String category) {
        postRepository.save(new PostEntity(accountId, title, content, category));
    }

    public Page<GetPostsData> getPosts(Pageable pageable) {
        return postRepositorySupport.findAllData(pageable);
    }

    public Page<GetPostsData> getPosts(String category, Pageable pageable) {
        return postRepositorySupport.findAllByCategory(category, pageable);
    }

    public Page<GetPostsData> searchPostsByTitle(String title, Pageable pageable) {
        return postRepositorySupport.findByTitle(title, pageable);
    }

    public PostEntity getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NullPointerException("Unavailable Post"));
    }

    public List<PostCategories> getCategoryCounts() {
        return postRepositorySupport.findCategoryCounts();
    }

    @Transactional
    public void updatePost(Long accountId, Long postId, String title, String content, String category) {
        PostEntity postEntity = postRepository.getById(postId);
        Preconditions.checkState(postEntity.getAccountId() == accountId, "Unauthorized");
        postEntity.setTitle(title);
        postEntity.setContent(content);
        postEntity.setCategory(category);
    }

    @Transactional
    public void deletePost(Long accountId, Long postId) {
        PostEntity postEntity = postRepository.getById(postId);
        Preconditions.checkState(postEntity.getAccountId() == accountId, "Unauthorized");
        postRepository.deleteById(postId);
    }
}
