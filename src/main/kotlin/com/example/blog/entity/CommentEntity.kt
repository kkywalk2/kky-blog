package com.example.blog.entity

import com.example.blog.dto.CommentDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val accountName: String,

    val accountId: Long,

    val postId: Long,

    val content: String,

    @CreationTimestamp
    private val createAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    private val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun copy(
        content: String = this.content,
        createAt: LocalDateTime = this.createAt,
        updatedAt: LocalDateTime = this.updatedAt
    ): CommentEntity {
        return CommentEntity(
            id = id,
            postId = postId,
            accountName = accountName,
            accountId = accountId,
            content = content,
            createAt = createAt,
            updatedAt = updatedAt
        )
    }

    fun toDto(): CommentDto {
        return CommentDto(
            id = id,
            postId = postId,
            accountName = accountName,
            content = content,
            createAt = createAt,
            updatedAt = updatedAt
        )
    }
}