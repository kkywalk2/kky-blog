package com.example.blog.service;

import com.example.blog.repository.CommentRepository;
import com.example.blog.entity.CommentEntity;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(Long accountId, String accountName, Long postId, String content) {
        commentRepository.save(new CommentEntity(accountId, accountName, postId, content));
    }
}
