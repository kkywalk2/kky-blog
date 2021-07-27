package com.example.blog.service;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;
import com.example.blog.repository.AccountRepository;
import com.example.blog.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.blog.entity.AccountEntity;
import com.example.blog.entity.PostEntity;

import com.google.common.base.Preconditions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public void createPost(Long accountId, String title, String content, String category) {
        postRepository.save(new PostEntity(accountId, title, content, category));
    }

    public List<GetPostsData> getPosts(Long accountId) {
        return postRepository.findAllByAccountID(accountId);
    }

    public List<GetPostsData> getPosts(Long accountId, String category) {
        return postRepository.findAllByAccountIDAndCategory(accountId, category);
    }

    public PostEntity getPost(Long accountId, Long postId) {
        PostEntity post = postRepository.findById(postId).orElseThrow(NullPointerException::new);
        Preconditions.checkState(accountId.equals(post.getAccountId()),"Authorization Failed");
        return post;
    }

    public List<PostCategories> getCategoryCounts(Long accountId) {
        return postRepository.findCategoryCounts(accountId);
    }
}
