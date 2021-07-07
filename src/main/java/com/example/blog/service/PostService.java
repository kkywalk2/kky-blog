package com.example.blog.service;

import com.example.blog.repository.AccountRepository;
import com.example.blog.repository.PostRepository;

import javax.transaction.Transactional;

import com.example.blog.entity.PostEntity;

import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    PostService(PostRepository postRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public boolean createPost(Long accountid, String content, String category) {
        if (accountRepository.existsById(accountid)) {
            PostEntity post = postRepository.save(new PostEntity(accountid, content, category));
            return postRepository.existsById(post.getId());
        } else {
            return false;
        }
    }
}
