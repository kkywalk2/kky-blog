package com.example.blog.dto

import java.time.LocalDateTime

data class CategoryDto(
    val category: String,
    val count: Long
)

data class PostDto(
    val id: Long,
    val title: String,
    val category: String,
    val views: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val content: String,
    val comments: List<CommentDto>
)

data class GetPostsResponse(
    val posts: List<PostDto>,
    val cursor: String
)
