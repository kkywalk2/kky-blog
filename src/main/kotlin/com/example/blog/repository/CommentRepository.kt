package com.example.blog.repository

import com.example.blog.dto.CommentDto
import com.example.blog.dto.CreateCommentRequest
import com.example.blog.entity.CommentsEntity
import com.example.blog.entity.Posts
import org.jetbrains.exposed.dao.id.EntityID
import org.springframework.stereotype.Repository

@Repository
class CommentRepository {

    fun save(accountId: Long, accountName: String, request: CreateCommentRequest): CommentDto {
        return CommentsEntity.new {
            this.accountId = accountId
            this.accountName = accountName
            this.postId = EntityID(request.postId, Posts)
            this.content = request.content
        }.toDto()
    }

    private fun CommentsEntity.toDto(): CommentDto {
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
