package com.example.blog.service

import com.example.blog.dto.*
import com.example.blog.entity.Post
import com.example.blog.repository.PostRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {

    fun createPost(accountId: Long, createPostRequest: CreatePostRequest): PostDto {
        return postRepository.save(accountId, createPostRequest).toDto()
    }

    //TODO: Page 객체를 사용하지 않는 것이 좋음, none offset pagination 으로 수정예정
    fun getPosts(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto> {
        return postRepository.getByTitleAndCategory(pageable, title, category).map { it.toDto() }
    }

    fun getPost(postId: Long): PostDto {
        return postRepository.findById(postId).orElseThrow { NotFoundException() }.toDto(true)
    }

    fun getCategoryCounts(): List<CategoryDto> {
        return postRepository.getCategoryCounts()
    }

    fun updatePost(accountId: Long, postId: Long, updatePostRequest: UpdatePostRequest): PostDto {
        return postRepository.update(accountId, postId, updatePostRequest).toDto()
    }

    fun deletePost(accountId: Long, postId: Long): PostDto {
        return postRepository.delete(accountId, postId).toDto()
    }

    private fun Post.toDto(includeComments: Boolean = false): PostDto {
        return PostDto(
            id.value,
            title,
            category,
            views,
            createdAt,
            updatedAt,
            content,
            comments = if(includeComments) {
                comments.map { c ->
                    CommentDto(
                        c.id.value,
                        c.postId.value,
                        c.accountName,
                        c.content,
                        c.createdAt,
                        c.updatedAt
                    )
                }
            } else {
                emptyList<CommentDto>()
            }
        )
    }
}