package com.example.blog.service

import com.example.blog.dto.CreatePostRequest
import com.example.blog.dto.PostDetailDto
import com.example.blog.dto.PostDto
import com.example.blog.dto.UpdatePostRequest
import com.example.blog.dto.CategoryDto
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
        return postRepository.save(accountId, createPostRequest)
    }

    //TODO: Page 객체를 사용하지 않는 것이 좋음, none offset pagination 으로 수정예정
    fun getPosts(pageable: Pageable, title: Optional<String>, category: Optional<String>): Page<PostDto> {
        return postRepository.getByTitleAndCategory(pageable, title, category)
    }

    fun getPost(postId: Long): PostDetailDto {
        return postRepository.findById(postId).orElseThrow { NotFoundException() }
    }

    fun getCategoryCounts(): List<CategoryDto> {
        return postRepository.getCategoryCounts()
    }

    fun updatePost(accountId: Long, postId: Long, updatePostRequest: UpdatePostRequest): PostDetailDto {
        return postRepository.update(accountId, postId, updatePostRequest)
    }

    fun deletePost(accountId: Long, postId: Long): PostDto {
        return postRepository.delete(accountId, postId)
    }
}