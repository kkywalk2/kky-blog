package com.example.blog.post.services

import com.example.blog.dto.*
import com.example.blog.exception.ResourceNotFoundException
import com.example.blog.post.domains.CreatePost
import com.example.blog.post.domains.PostDomain
import com.example.blog.post.domains.UpdatePost
import com.example.blog.post.services.ports.PostRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
) {

    fun create(userId: Long, createPost: CreatePost): PostDto {
        val post = PostDomain.createPost(userId, createPost)
        return postRepository.save(post).toDto()
    }

    fun getPosts(
        limit: Int,
        title: String?,
        category: String?,
        lastId: Long? = null,
        lastCreatedAt: LocalDateTime? = null
    ): List<PostDto> {
        return postRepository.getByTitleAndCategory(limit, title, category, lastId, lastCreatedAt).map { it.toDto() }
    }

    fun getPost(postId: Long): PostDto {
        return postRepository.findById(postId).orElseThrow { NotFoundException() }.toDto()
    }

    fun getCategoryCounts(): List<CategoryDto> {
        return postRepository.getCategoryCounts()
    }

    fun updatePost(userId: Long, postId: Long, updatePost: UpdatePost): PostDto {
        return postRepository
            .findById(postId)
            .map { it.update(userId, updatePost) }
            .map { postRepository.save(it).toDto() }
            .orElseThrow { ResourceNotFoundException() }
    }

    fun deletePost(userId: Long, postId: Long): PostDto {
        return postRepository
            .findById(postId)
            .map { it.delete(userId) }
            .map { postRepository.save(it).toDto() }
            .orElseThrow { ResourceNotFoundException() }
    }

    private fun PostDomain.toDto(): PostDto {
        return PostDto(
            id,
            title,
            category,
            views,
            createdAt,
            updatedAt,
            content,
            comments = comments.map { c ->
                CommentDto(
                    id,
                    userName = "",
                    c.content,
                    c.createdAt,
                    c.updatedAt
                )
            }
        )
    }
}
