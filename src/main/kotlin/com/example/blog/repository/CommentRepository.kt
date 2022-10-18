package com.example.blog.repository

import com.example.blog.dto.CommentDto
import com.example.blog.dto.CreateCommentRequest
import com.example.blog.entity.Comments
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Component

@Component
class CommentRepository {

    fun save(accountId: Long, accountName: String, request: CreateCommentRequest): CommentDto {
        val insertedComment = Comments.insert {
            it[Comments.accountId] = accountId
            it[Comments.accountName] = accountName
            it[Comments.postId] = request.postId
            it[Comments.content] = request.content
        }

        return CommentDto(
            insertedComment[Comments.id].value,
            insertedComment[Comments.postId].value,
            insertedComment[Comments.accountName],
            insertedComment[Comments.content],
            insertedComment[Comments.createdAt],
            insertedComment[Comments.updatedAt]
        )
    }
}
