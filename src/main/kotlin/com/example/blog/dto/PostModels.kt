package com.example.blog.dto

import java.time.LocalDateTime
import javax.validation.constraints.Size

data class CategoryDto(
    val category: String,
    val count: Long
)

data class PostDto(
    val id: Long,
    val title: String,
    val category: String,
    val views: Long,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
)

data class PostDetailDto(
    val id: Long,
    val title: String,
    val category: String,
    val views: Long,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime,
    val content: String,
    val comments: List<CommentDto>
)

data class PostRequest(
    @field:Size(max = 100)
    val title: String,
    val content: String,
    @field:Size(min = 1, max = 30)
    val category: String
)

typealias CreatePostRequest = PostRequest
typealias UpdatePostRequest = PostRequest