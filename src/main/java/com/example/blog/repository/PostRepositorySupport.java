package com.example.blog.repository;

import com.example.blog.dto.post.GetPostsData;
import com.example.blog.dto.post.PostCategories;
import com.example.blog.entity.PostEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.blog.entity.QPostEntity.postEntity;

import java.util.List;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositorySupport(final JPAQueryFactory jpaQueryFactory) {
        super(PostEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<GetPostsData> findAllData(Pageable pageable) {
        QueryResults<GetPostsData> result = jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(GetPostsData.class, postEntity.id, postEntity.title,
                        postEntity.category, postEntity.views, postEntity.createAt))
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public List<PostCategories> findCategoryCounts() {
        return jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(PostCategories.class, postEntity.category, postEntity.category.count()))
                .groupBy(postEntity.category).fetch();
    }

    public Page<GetPostsData> findAllByCategory(String category, Pageable pageable) {
        QueryResults<GetPostsData> result = jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(GetPostsData.class, postEntity.id, postEntity.title,
                        postEntity.category, postEntity.views, postEntity.createAt))
                .where(postEntity.category.eq(category))
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public Page<GetPostsData> findByTitle(String title, Pageable pageable) {
        QueryResults<GetPostsData> result = jpaQueryFactory.from(postEntity)
                .select(Projections.constructor(GetPostsData.class, postEntity.id, postEntity.title,
                        postEntity.category, postEntity.views, postEntity.createAt))
                .where(postEntity.title.contains(title))
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
