package com.example.blog.repository

import com.example.blog.entity.Images
import com.example.blog.entity.Image
import org.springframework.stereotype.Repository

@Repository
class ImageRepository {

    fun save(originalName: String, filePath: String): Image {
        return Image.new {
            this.originalName = originalName
            this.filePath = filePath
        }
    }

    fun findByFilePath(filePath: String): Image {
        return Image.find { Images.filePath eq filePath }.first()
    }
}
