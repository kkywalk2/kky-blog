package com.example.blog.service

import com.example.blog.dto.CommentDto
import com.example.blog.dto.CreateCommentRequest
import com.example.blog.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService (
    private val commentRepository: CommentRepository
) {
    fun addComment(accountId: Long, accountName: String, request: CreateCommentRequest): CommentDto {
        return commentRepository.save(accountId, accountName, request)
    }
}