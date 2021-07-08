package com.example.blog.service;

import com.example.blog.repository.AccountRepository;
import com.example.blog.repository.PostRepository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.blog.entity.AccountEntity;
import com.example.blog.entity.PostEntity;

import org.springframework.security.core.userdetails.UserDetails;
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
    public boolean createPost(UserDetails userDetails, String content, String category) {
        AccountEntity account = accountRepository.findByAccountName(userDetails.getUsername());
        PostEntity post = postRepository.save(new PostEntity(account.getId(), content, category));
        return postRepository.existsById(post.getId());
    }

    public List<PostEntity> getPosts(UserDetails userDetails) {
        AccountEntity account = accountRepository.findByAccountName(userDetails.getUsername());
        return account.getPosts();
    }
}
