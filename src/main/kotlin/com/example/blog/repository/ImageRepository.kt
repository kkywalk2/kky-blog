package com.example.blog.repository

import com.example.blog.entity.ImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<ImageEntity, Long> {
    fun findByFilePath(filePath: String): ImageEntity
}
