package com.example.blog.post.services

import com.example.blog.dto.*
import com.example.blog.exception.ResourceNotFoundException
import com.example.blog.post.domains.CreatePost
import com.example.blog.post.domains.PostDomain
import com.example.blog.post.domains.UpdatePost
import com.example.blog.post.services.ports.PostRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
) {

    fun create(userId: Long, createPost: CreatePost): PostDto {
        val post = PostDomain.createPost(userId, createPost)
        return postRepository.save(post).toDto()
    }

    //TODO: Page 객체를 사용하지 않는 것이 좋음, none offset pagination 으로 수정예정
    fun getPosts(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto> {
        return postRepository.getByTitleAndCategory(pageable, title, category).map { it.toDto() }
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
                    accountName = "",
                    c.content,
                    c.createdAt,
                    c.updatedAt
                )
            }
        )
    }
}
