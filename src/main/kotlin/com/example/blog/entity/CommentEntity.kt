package com.example.blog.entity

import com.example.blog.dto.CommentDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "account_name")
    val accountName: String,

    @Column(name = "account_id")
    val accountId: Long,

    @Column(name = "post_id")
    val postId: Long,

    @Column(name = "content")
    val content: String,

    @CreationTimestamp
    @Column(name = "create_at")
    private val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at")
    private val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun copy(
        content: String = this.content,
        createAt: LocalDateTime = this.createdAt,
        updatedAt: LocalDateTime = this.updatedAt
    ): CommentEntity {
        return CommentEntity(
            id = id,
            postId = postId,
            accountName = accountName,
            accountId = accountId,
            content = content,
            createdAt = createAt,
            updatedAt = updatedAt
        )
    }

    fun toDto(): CommentDto {
        return CommentDto(
            id = id,
            postId = postId,
            accountName = accountName,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
