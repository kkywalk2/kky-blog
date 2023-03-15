package com.example.blog.repository

import com.example.blog.dto.CreateCommentRequest
import com.example.blog.entity.Comment
import com.example.blog.entity.Posts
import org.jetbrains.exposed.dao.id.EntityID
import org.springframework.stereotype.Repository

@Repository
class CommentRepository {

    fun save(accountId: Long, accountName: String, request: CreateCommentRequest): Comment {
        return Comment.new {
            this.accountId = accountId
            this.accountName = accountName
            this.postId = EntityID(request.postId, Posts)
            this.content = request.content
        }
    }
}
