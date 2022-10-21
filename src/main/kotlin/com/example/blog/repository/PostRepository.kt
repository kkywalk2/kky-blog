package com.example.blog.repository

import com.example.blog.dto.*
import com.example.blog.entity.Posts
import com.example.blog.entity.PostsEntity
import org.jetbrains.exposed.dao.load
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.springframework.stereotype.Component

@Component
class PostRepository {

    fun save(accountId: Long, request: CreatePostRequest): PostDto {
        val insertedPost = Posts.insert {
            it[title] = request.title
            it[content] = request.content
            it[category] = request.category
            it[Posts.accountId] = accountId
        }

        return PostDto(
            insertedPost[Posts.id].value,
            insertedPost[Posts.title],
            insertedPost[Posts.category],
            insertedPost[Posts.views],
            insertedPost[Posts.createdAt],
            insertedPost[Posts.updatedAt]
        )
    }

    fun update(accountId: Long, postId: Long, request: UpdatePostRequest): PostDetailDto {
        val expression = (Posts.accountId eq accountId) and (Posts.id eq postId)

        Posts.update({ expression }) {
            it[title] = request.title
            it[content] = request.content
            it[category] = request.category
        }

        return PostsEntity.find(expression).first().toDetailDto()
    }

    fun delete(accountId: Long, postId: Long): PostDto {
        val expression = (Posts.accountId eq accountId) and (Posts.id eq postId)

        Posts.deleteWhere { expression }

        return Posts.select { expression }.first().toDto()
    }

    fun findById(id: Long): Optional<PostDetailDto> {
        return Optional.ofNullable(PostsEntity.findById(id))
            .map { it.load(PostsEntity::comments) }
            .map { it.toDetailDto() }
    }

    fun getByTitleAndCategory(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto> {
        val result = Posts
            .slice(Posts.id, Posts.title, Posts.category, Posts.views, Posts.createdAt, Posts.updatedAt)
            .selectAll()
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .orderBy(Posts.createdAt to SortOrder.DESC)
            .limit(pageable.pageSize, pageable.offset)
            .map { it.toDto() }

        val count = Posts
            .selectAll()
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .count()

        return PageImpl(result, pageable, count)
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

    private fun PostsEntity.toDetailDto(): PostDetailDto {
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
