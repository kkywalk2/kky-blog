package com.example.blog.post.services.ports

import com.example.blog.dto.CategoryDto
import com.example.blog.post.domains.PostDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

interface PostRepository {

    fun save(postDomain: PostDomain): PostDomain

    fun findById(id: Long): Optional<PostDomain>

    fun getByTitleAndCategory(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDomain>

    fun getCategoryCounts(): List<CategoryDto>

}
