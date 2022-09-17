package com.example.blog.repository;

import com.example.blog.dto.PostDto;
import com.example.blog.dto.CategoryDto;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;
import java.util.Optional;

import static com.example.blog.entity.QPostEntity.postEntity;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<PostDto> findAllData(Pageable pageable, Optional<String> title, Optional<String> category) {
        JPAQuery<PostDto> query = jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(
                        PostDto.class,
                        postEntity.id,
                        postEntity.title,
                        postEntity.category,
                        postEntity.views,
                        postEntity.createAt,
                        postEntity.updatedAt))
                .where(containsTitle(title), equalCategory(category))
                .orderBy(postEntity.createAt.desc());

        if(pageable.isPaged())
            query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        //TODO: fetchResults deprecated 됨, none offset pagination 으로 수정하면서 제거 예정
        QueryResults<PostDto> result = query.fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public List<CategoryDto> findCategoryCounts() {
        return jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(
                        CategoryDto.class,
                        postEntity.category,
                        postEntity.category.count()))
                .groupBy(postEntity.category)
                .fetch();
    }

    public BooleanExpression containsTitle(Optional<String> title) {
        return title.map(postEntity.title::contains).orElse(null);
    }

    public BooleanExpression equalCategory(Optional<String> category) {
        return category.map(postEntity.category::eq).orElse(null);
    }
}
