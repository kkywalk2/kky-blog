package com.example.blog.image.infrastructures

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
