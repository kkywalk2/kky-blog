package com.example.blog.post.domains

data class CreatePost(
    val userId: Long,
    val views: Long,
    val title: String,
    val content: String,
    val category: String,
)
