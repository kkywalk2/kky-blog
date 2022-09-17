package com.example.blog.service

import com.example.blog.dto.CreatePostRequest
import com.example.blog.dto.PostDetailDto
import com.example.blog.dto.PostDto
import com.example.blog.dto.UpdatePostRequest
import com.example.blog.dto.CategoryDto
import com.example.blog.entity.PostEntity
import com.example.blog.exception.UnauthorizedException
import com.example.blog.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository
) {
    @Transactional
    fun createPost(accountId: Long, createPostRequest: CreatePostRequest): PostDto {
        return postRepository.save(createPostRequest.toEntity(accountId)).toDto()
    }

    //TODO: Page 객체를 사용하지 않는 것이 좋음, none offset pagination 으로 수정예정
    @Transactional(readOnly = true)
    fun getPosts(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto> {
        return postRepository.findAllData(pageable, title, category)
    }

    @Transactional(readOnly = true)
    fun getPost(postId: Long): PostDetailDto {
        return postRepository.findById(postId)
            .orElseThrow { NullPointerException("Unavailable Post") }
            .toDetailDto()
    }

    @Transactional(readOnly = true)
    fun getCategoryCounts(): List<CategoryDto> {
        return postRepository.findCategoryCounts()
    }

    @Transactional
    fun updatePost(accountId: Long, postId: Long, updatePostRequest: UpdatePostRequest): PostDetailDto {
        val postEntity = postRepository.getById(postId)
        if (postEntity.accountId != accountId) throw UnauthorizedException()
        return postEntity.let { postRepository.save(updatePostRequest.toEntity(postEntity)) }.toDetailDto()
    }

    @Transactional
    fun deletePost(accountId: Long, postId: Long): PostDto {
        val postEntity = postRepository.getById(postId)
        if (postEntity.accountId != accountId) throw UnauthorizedException()
        return postEntity.apply { postRepository.delete(postEntity) }.toDto()
    }

    private fun CreatePostRequest.toEntity(accountId: Long): PostEntity {
        return PostEntity(
            accountId = accountId,
            title = title,
            content = content,
            category = category
        )
    }

    private fun UpdatePostRequest.toEntity(postEntity: PostEntity): PostEntity {
        return postEntity.copy(
            title = title,
            content = content,
            category = category
        )
    }
}