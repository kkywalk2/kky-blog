package com.example.blog.post.infrastuctures

import com.example.blog.dto.*
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

    fun save(accountId: Long, request: CreatePostRequest): PostDao {
        return PostDao.new {
            this.userId = accountId
            title = request.title
            content = request.content
            category = request.category
        }
    }

    fun update(accountId: Long, postId: Long, request: UpdatePostRequest): PostDao {
        val expression = (Posts.userId eq accountId) and (Posts.id eq postId)

        return PostDao.find(expression)
            .first()
            .apply {
                title = request.title
                content = request.content
                category = request.category
            }
    }

    fun delete(accountId: Long, postId: Long): PostDao {
        val expression = (Posts.userId eq accountId) and (Posts.id eq postId)

        return PostDao.find(expression)
            .first()
            .apply { this.delete() }
    }

    fun findById(id: Long): Optional<PostDao> {
        return Optional.ofNullable(PostDao.findById(id))
            .map { it.load(PostDao::comments) }
    }

    fun getByTitleAndCategory(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDao> {
        val query = Posts
            .select { Posts.deleted eq false }
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .orderBy(Posts.createdAt to SortOrder.DESC)

        PostDao.find { Posts.deleted eq false }
        if (pageable.isPaged) query.limit(pageable.pageSize, pageable.offset)

        val count = Posts
            .select { Posts.deleted eq false }
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .count()

        return PageImpl(PostDao.wrapRows(query).toList(), pageable, count)
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
