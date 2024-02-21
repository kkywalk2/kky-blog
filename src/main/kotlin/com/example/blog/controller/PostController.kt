package com.example.blog.controller

import com.example.blog.dto.*
import com.example.blog.service.PostService
import com.example.blog.user.domains.UserDomain
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/post")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(
        @RequestBody request: @Valid CreatePostRequest,
        @AuthenticationPrincipal userDomain: UserDomain,
    ): PostDto {
        return postService.createPost(userDomain.id, request)
    }

    @GetMapping
    fun getPosts(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "per_page", defaultValue = "0") perPage: Int,
        @RequestParam(value = "title") title: Optional<String>,
        @RequestParam(value = "category") category: Optional<String>
    ): Page<PostDto> {
        val pageable = if (perPage == 0) Pageable.unpaged() else PageRequest.of(page, perPage)
        return postService.getPosts(pageable, title, category)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable("id") id: Long): PostDto {
        return postService.getPost(id)
    }

    @GetMapping("/category")
    fun getCategory(): List<CategoryDto> {
        return postService.getCategoryCounts()
    }

    @PutMapping("/{id}")
    fun updatePost(
        @PathVariable("id") id: Long,
        @RequestBody request: @Valid UpdatePostRequest,
        @AuthenticationPrincipal userDomain: UserDomain,
    ): PostDto {
        return postService.updatePost(userDomain.id, id, request)
    }

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable("id") id: Long,
        @AuthenticationPrincipal userDomain: UserDomain,
    ): PostDto {
        return postService.deletePost(userDomain.id, id)
    }
}