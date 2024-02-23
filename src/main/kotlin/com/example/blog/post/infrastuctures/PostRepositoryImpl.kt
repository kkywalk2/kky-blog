package com.example.blog.post.infrastuctures

import com.example.blog.dto.CategoryDto
import com.example.blog.exception.ResourceNotFoundException
import com.example.blog.post.domains.Comment
import com.example.blog.post.domains.PostDomain
import com.example.blog.post.services.ports.PostRepository
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
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
        pageable: Pageable,
        title: Optional<String>,
        category: Optional<String>
    ): Page<PostDomain> {
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

        return PageImpl(PostDao.wrapRows(query).toList(), pageable, count).map { it.toDomain() }
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
