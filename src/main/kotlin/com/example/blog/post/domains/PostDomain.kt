package com.example.blog.post.domains

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

    fun delete(): PostDomain {
        return this.copy(deleted = true)
    }

    companion object {
        fun createPost(createPost: CreatePost, now: LocalDateTime = LocalDateTime.now()): PostDomain {
            return PostDomain(
                id = 0,
                userId = createPost.userId,
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
