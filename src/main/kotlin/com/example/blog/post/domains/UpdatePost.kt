package com.example.blog.post.domains

data class UpdatePost (
    val views: Long,
    val title: String,
    val content: String,
    val category: String,
)
