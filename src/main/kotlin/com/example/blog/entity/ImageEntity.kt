package com.example.blog.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,

    private val originalName: String,

    private val filePath: String,

    @CreationTimestamp
    private val createAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    private val UpdatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun getPath(): Path {
        return Paths.get(filePath)
    }
}
