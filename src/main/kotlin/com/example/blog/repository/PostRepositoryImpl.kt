package com.example.blog.repository

import com.example.blog.dto.CategoryDto
import com.example.blog.dto.PostDto
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.*

import com.example.blog.entity.QPostEntity.postEntity

@Repository
class PostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : PostRepositoryCustom {
    override fun findAllData(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto> {
        val query = jpaQueryFactory.from(postEntity)
            .select(
                Projections.constructor(
                    PostDto::class.java,
                    postEntity.id,
                    postEntity.title,
                    postEntity.category,
                    postEntity.views,
                    postEntity.createdAt,
                    postEntity.updatedAt
                )
            )
            .where(containsTitle(title), equalCategory(category))
            .orderBy(postEntity.createdAt.desc())
        if (pageable.isPaged) query.offset(pageable.offset).limit(pageable.pageSize.toLong())

        //TODO: fetchResults deprecated 됨, none offset pagination 으로 수정하면서 제거 예정
        val result = query.fetchResults()
        return PageImpl(result.results, pageable, result.total)
    }

    override fun findCategoryCounts(): List<CategoryDto> {
        return jpaQueryFactory.from(postEntity)
            .select(
                Projections.constructor(
                    CategoryDto::class.java,
                    postEntity.category,
                    postEntity.category.count()
                )
            )
            .groupBy(postEntity.category)
            .fetch()
    }

    fun containsTitle(title: Optional<String>): BooleanExpression? {
        return title
            .map { str: String -> postEntity.title.contains(str) }
            .orElse(null)
    }

    fun equalCategory(category: Optional<String>): BooleanExpression? {
        return category
            .map { right: String -> postEntity.category.eq(right) }
            .orElse(null)
    }
}
