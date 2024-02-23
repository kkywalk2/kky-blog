package com.example.blog.post.services

import com.example.blog.dto.CommentDto
import com.example.blog.dto.CreateCommentRequest
import com.example.blog.post.infrastuctures.CommentDao
import com.example.blog.post.infrastuctures.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository
) {
    fun addComment(accountId: Long, accountName: String, request: CreateCommentRequest): CommentDto {
        return commentRepository.save(accountId, accountName, request).toDto()
    }

    private fun CommentDao.toDto(): CommentDto {
        return CommentDto(
            id.value,
            postId.value,
            accountName,
            content,
            createdAt,
            updatedAt
        )
    }
}
