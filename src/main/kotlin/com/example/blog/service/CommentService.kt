package com.example.blog.service

import com.example.blog.entity.CommentEntity
import com.example.blog.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService (
    private val commentRepository: CommentRepository
) {
    fun addComment(accountId: Long, accountName: String, postId: Long, content: String) {
        commentRepository.save(CommentEntity(
            accountId = accountId,
            accountName = accountName,
            postId = postId,
            content = content
        ))
    }
}