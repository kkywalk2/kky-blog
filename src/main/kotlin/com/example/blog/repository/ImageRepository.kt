package com.example.blog.repository

import com.example.blog.entity.Images
import com.example.blog.entity.ImagesEntity
import org.springframework.stereotype.Repository

@Repository
class ImageRepository {

    fun save(originalName: String, filePath: String): ImagesEntity {
        return ImagesEntity.new {
            this.originalName = originalName
            this.filePath = filePath
        }
    }

    fun findByFilePath(filePath: String): ImagesEntity {
        return ImagesEntity.find { Images.filePath eq filePath }.first()
    }
}
