package com.example.blog.repository

import com.example.blog.dto.CategoryDto
import com.example.blog.dto.PostDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface PostRepositoryCustom {
    fun findAllData(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto>
    fun findCategoryCounts(): List<CategoryDto>
}
