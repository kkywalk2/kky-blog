package com.example.blog.post.infrastuctures

import com.example.blog.dto.CategoryDto
import com.example.blog.exception.ResourceNotFoundException
import com.example.blog.post.domains.Comment
import com.example.blog.post.domains.PostDomain
import com.example.blog.post.services.ports.PostRepository
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class PostRepositoryImpl : PostRepository {

    override fun save(postDomain: PostDomain): PostDomain {
        return if (postDomain.id == 0L) {
            PostDao.new {
                this.userId = postDomain.userId
                title = postDomain.title
                content = postDomain.content
                category = postDomain.category
            }.toDomain()
        } else {
            PostDao
                .findById(postDomain.id)
                ?.apply {
                    title = postDomain.title
                    content = postDomain.content
                    category = postDomain.category
                }?.toDomain() ?: throw ResourceNotFoundException()
        }
    }

    override fun findById(id: Long): Optional<PostDomain> {
        return Optional
            .ofNullable(PostDao.findById(id))
            .map { it.load(PostDao::comments) }
            .map { it.toDomain(true) }
    }

    override fun getByTitleAndCategory(
        limit: Int,
        title: String?,
        category: String?,
        lastId: Long?,
        lastCreatedAt: LocalDateTime?
    ): List<PostDomain> {
        val query = Posts
            .select { Posts.deleted eq false }
            .andWhere(title) { Posts.title like "%$it%" }
            .andWhere(category) { Posts.category eq it }
            .andWhere2(lastId, lastCreatedAt) { id, createdAt ->
                (Posts.createdAt less createdAt) or ((Posts.id less id) and (Posts.createdAt eq createdAt))
            }
            .orderBy(Posts.createdAt to SortOrder.DESC, Posts.id to SortOrder.DESC)
            .limit(limit)

        return PostDao.wrapRows(query).toList().map { it.toDomain() }
    }

    override fun getCategoryCounts(): List<CategoryDto> {
        return Posts.slice(Posts.category, Posts.category.count())
            .selectAll()
            .groupBy(Posts.category)
            .map { CategoryDto(it[Posts.category], it[Posts.category.count()]) }
    }

    private fun PostDao.toDomain(includeComments: Boolean = false): PostDomain {
        return PostDomain(
            id = id.value,
            userId = userId,
            title = title,
            category = category,
            views = views,
            createdAt = createdAt,
            updatedAt = updatedAt,
            content = content,
            deleted = deleted,
            comments = if (includeComments) {
                comments.map { c ->
                    Comment(
                        c.accountId,
                        c.accountName,
                        c.content,
                        c.createdAt,
                        c.updatedAt
                    )
                }
            } else {
                emptyList()
            }
        )
    }

    private fun <T> Query.andWhere(
        parameter: T?,
        andPart: SqlExpressionBuilder.(T) -> Op<Boolean>
    ) = apply {
        parameter?.let {
            val expr = Op.build { andPart(it) }

            this.adjustWhere {
                if (this == null) expr
                else this and expr
            }
        }
    }

    private fun <U, T> Query.andWhere2(
        parameter1: U?,
        parameter2: T?,
        andPart: SqlExpressionBuilder.(U, T) -> Op<Boolean>
    ) = apply {
        parameter1?.let {
            parameter2?.let {
                val expr = Op.build { andPart(parameter1, parameter2) }

                this.adjustWhere {
                    if (this == null) expr
                    else this and expr
                }
            }
        }
    }

}
