package com.example.blog.post.domains

data class CreatePost(
    val views: Long,
    val title: String,
    val content: String,
    val category: String,
)
