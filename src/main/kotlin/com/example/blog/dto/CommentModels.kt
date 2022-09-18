package com.example.blog.dto

import java.time.LocalDateTime

data class CommentDto(
    val id: Long,
    val postId: Long,
    val accountName: String,
    val content: String,
    val createAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class CreateCommentRequest (
    val postId: Long,
    val content: String
)