package com.example.blog.post.services.ports

import com.example.blog.dto.CategoryDto
import com.example.blog.post.domains.PostDomain
import java.time.LocalDateTime
import java.util.Optional

interface PostRepository {

    fun save(postDomain: PostDomain): PostDomain

    fun findById(id: Long): Optional<PostDomain>

    fun getByTitleAndCategory(
        limit: Int,
        title: String?,
        category: String?,
        lastId: Long?,
        lastCreatedAt: LocalDateTime?
    ): List<PostDomain>

    fun getCategoryCounts(): List<CategoryDto>

}
