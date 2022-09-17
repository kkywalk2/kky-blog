package com.example.blog.entity

import com.example.blog.dto.PostDetailDto
import com.example.blog.dto.PostDto
import com.google.common.collect.Lists
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.Long
import kotlin.String

@Entity
@SQLDelete(sql = "UPDATE post_entity SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(indexes = [Index(name = "title_index", columnList = "title")])
class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val accountId: Long,

    val views: Long = 0,

    val deleted: Boolean = false,

    val title: String,

    val content: String,

    val category: String,

    @CreationTimestamp
    val createAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY)
    val comments: List<CommentEntity> = Lists.newArrayList()
) {
    fun copy(
        views: Long = this.views,
        deleted: Boolean = this.deleted,
        title: String = this.title,
        content: String = this.content,
        category: String = this.category,
        createAt: LocalDateTime = this.createAt,
        updatedAt: LocalDateTime = this.updatedAt,
        comments: List<CommentEntity> = this.comments
    ): PostEntity {
        return PostEntity(
            id = id,
            accountId = accountId,
            views = views,
            deleted = deleted,
            title = title,
            content = content,
            category = category,
            createAt = createAt,
            updatedAt = updatedAt,
            comments = comments
        )
    }

    fun toDto(): PostDto {
        return PostDto(
            id = id,
            title = title,
            category = category,
            views = views,
            createAt = createAt,
            updateAt = updatedAt
        )
    }

    fun toDetailDto(): PostDetailDto {
        return PostDetailDto(
            id = id,
            title = title,
            category = category,
            views = views,
            createAt = createAt,
            updateAt = updatedAt,
            content = content,
            comments = comments.map { it.toDto() }
        )
    }
}