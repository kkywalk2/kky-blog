package com.example.blog.post.infrastuctures

import com.example.blog.dto.CreateCommentRequest
import org.jetbrains.exposed.dao.id.EntityID
import org.springframework.stereotype.Repository

@Repository
class CommentRepository {

    fun save(accountId: Long, accountName: String, request: CreateCommentRequest): CommentDao {
        return CommentDao.new {
            this.accountId = accountId
            this.accountName = accountName
            this.postId = EntityID(request.postId, Posts)
            this.content = request.content
        }
    }
}
