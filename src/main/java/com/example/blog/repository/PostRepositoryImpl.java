package com.example.blog.repository;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;

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

    public Page<GetPostsData> findAllData(Pageable pageable, Optional<String> title, Optional<String> category) {
        JPAQuery<GetPostsData> query = jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(
                        GetPostsData.class,
                        postEntity.id,
                        postEntity.title,
                        postEntity.category,
                        postEntity.views,
                        postEntity.createAt))
                .where(containsTitle(title), equalCategory(category))
                .orderBy(postEntity.createAt.desc());

        if(pageable.isPaged())
            query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        QueryResults<GetPostsData> result = query.fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public List<PostCategories> findCategoryCounts() {
        return jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(
                        PostCategories.class,
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
