package com.example.blog.post.domains

import com.example.blog.exception.InvalidAccessException
import com.example.blog.user.domains.UserDomain
import java.time.LocalDateTime

data class PostDomain(
    val id: Long = 0,
    val userId: Long,
    val views: Long,
    val deleted: Boolean,
    val title: String,
    val content: String,
    val category: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val comments: List<Comment>,
) {

    private val newComments: MutableList<Comment> = mutableListOf()

    fun addComment(userDomain: UserDomain, now: LocalDateTime = LocalDateTime.now()) {
        newComments.add(Comment(userDomain.id, userDomain.name, content, now, now))
    }

    fun delete(userId: Long): PostDomain {
        if (this.userId != userId) throw InvalidAccessException()

        return this.copy(deleted = true)
    }

    fun update(userId: Long, updatePost: UpdatePost): PostDomain {
        if (this.userId != userId) throw InvalidAccessException()

        return this.copy(
            views = updatePost.views,
            title = updatePost.title,
            content = updatePost.content,
            category = updatePost.category
        )
    }

    companion object {
        fun createPost(userId: Long, createPost: CreatePost, now: LocalDateTime = LocalDateTime.now()): PostDomain {
            return PostDomain(
                id = 0,
                userId = userId,
                views = createPost.views,
                deleted = false,
                title = createPost.title,
                content = createPost.content,
                category = createPost.category,
                createdAt = now,
                updatedAt = now,
                comments = listOf(),
            )
        }
    }
}

data class Comment(
    val commenterId: Long,
    val commenterName: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
