package com.example.blog.repository

import com.example.blog.dto.*
import com.example.blog.entity.Posts
import com.example.blog.entity.Post
import org.jetbrains.exposed.dao.load
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Repository

@Repository
class PostRepository {

    fun save(accountId: Long, request: CreatePostRequest): PostDto {
        return Post.new {
            this.accountId = accountId
            title = request.title
            content = request.content
            category = request.category
        }.toDto()
    }

    fun update(accountId: Long, postId: Long, request: UpdatePostRequest): PostDetailDto {
        val expression = (Posts.accountId eq accountId) and (Posts.id eq postId)

        return Post.find(expression)
            .first()
            .apply {
                title = request.title
                content = request.content
                category = request.category
            }.toDetailDto()
    }

    fun delete(accountId: Long, postId: Long): PostDto {
        val expression = (Posts.accountId eq accountId) and (Posts.id eq postId)

        return Post.find(expression)
            .first()
            .apply { this.delete() }
            .toDto()
    }

    fun findById(id: Long): Optional<PostDetailDto> {
        return Optional.ofNullable(Post.findById(id))
            .map { it.load(Post::comments) }
            .map { it.toDetailDto() }
    }

    fun getByTitleAndCategory(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto> {
        val query = Posts
            .slice(Posts.id, Posts.title, Posts.category, Posts.views, Posts.createdAt, Posts.updatedAt)
            .select { Posts.deleted eq false }
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .orderBy(Posts.createdAt to SortOrder.DESC)

        if(pageable.isPaged) query.limit(pageable.pageSize, pageable.offset)

        val count = Posts
            .select { Posts.deleted eq false }
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .count()

        return PageImpl(query.map { it.toDto() }, pageable, count)
    }

    fun getCategoryCounts(): List<CategoryDto> {
        return Posts.slice(Posts.category, Posts.category.count())
            .selectAll()
            .groupBy(Posts.category)
            .map { CategoryDto(it[Posts.category], it[Posts.category.count()]) }
    }

    private fun ResultRow.toDto(): PostDto {
        return PostDto(
            this[Posts.id].value,
            this[Posts.title],
            this[Posts.category],
            this[Posts.views],
            this[Posts.createdAt],
            this[Posts.updatedAt]
        )
    }

    private fun Post.toDto(): PostDto {
        return PostDto(
            id.value,
            title,
            category,
            views,
            createdAt,
            updatedAt
        )
    }

    private fun Post.toDetailDto(): PostDetailDto {
        return PostDetailDto(
            id.value,
            title,
            category,
            views,
            createdAt,
            updatedAt,
            content,
            comments.map { c ->
                CommentDto(
                    c.id.value,
                    c.postId.value,
                    c.accountName,
                    c.content,
                    c.createdAt,
                    c.updatedAt
                )
            }
        )
    }

    private fun <T> Query.andWhere(
        parameter: Optional<T>,
        andPart: SqlExpressionBuilder.(T) -> Op<Boolean>
    ) = apply {
        parameter.ifPresent {
            val expr = Op.build { andPart(it) }

            this.adjustWhere {
                if (this == null) expr
                else this and expr
            }
        }
    }
}
