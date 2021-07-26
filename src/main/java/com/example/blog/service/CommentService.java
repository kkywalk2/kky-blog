package com.example.blog.service;

import com.example.blog.entity.PostEntity;
import com.example.blog.repository.CommentRepository;
import com.example.blog.entity.CommentEntity;

import com.example.blog.repository.PostRepository;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void addComment(Long accountId, Long postId, String content) {
        PostEntity post = postRepository.findById(postId).orElseThrow(NullPointerException::new);
        Preconditions.checkState(accountId == post.getAccountId(),"Authorization Failed");
        commentRepository.save(new CommentEntity(accountId, postId, content));
    }
}

