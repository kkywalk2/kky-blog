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

    fun save(accountId: Long, request: CreatePostRequest): Post {
        return Post.new {
            this.accountId = accountId
            title = request.title
            content = request.content
            category = request.category
        }
    }

    fun update(accountId: Long, postId: Long, request: UpdatePostRequest): Post {
        val expression = (Posts.accountId eq accountId) and (Posts.id eq postId)

        return Post.find(expression)
            .first()
            .apply {
                title = request.title
                content = request.content
                category = request.category
            }
    }

    fun delete(accountId: Long, postId: Long): Post {
        val expression = (Posts.accountId eq accountId) and (Posts.id eq postId)

        return Post.find(expression)
            .first()
            .apply { this.delete() }
    }

    fun findById(id: Long): Optional<Post> {
        return Optional.ofNullable(Post.findById(id))
            .map { it.load(Post::comments) }
    }

    fun getByTitleAndCategory(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<Post> {
        val query = Posts
            .select { Posts.deleted eq false }
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .orderBy(Posts.createdAt to SortOrder.DESC)

        Post.find { Posts.deleted eq false }
        if (pageable.isPaged) query.limit(pageable.pageSize, pageable.offset)

        val count = Posts
            .select { Posts.deleted eq false }
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .count()

        return PageImpl(Post.wrapRows(query).toList(), pageable, count)
    }

    fun getCategoryCounts(): List<CategoryDto> {
        return Posts.slice(Posts.category, Posts.category.count())
            .selectAll()
            .groupBy(Posts.category)
            .map { CategoryDto(it[Posts.category], it[Posts.category.count()]) }
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
