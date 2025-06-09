package com.example.blog.post.controllers

import com.example.blog.config.Base64ObjectMapper
import com.example.blog.dto.*
import com.example.blog.post.domains.CreatePost
import com.example.blog.post.domains.UpdatePost
import com.example.blog.post.services.PostService
import com.example.blog.user.domains.UserDomain
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPost(
        @RequestBody @Valid request: CreatePost,
        @AuthenticationPrincipal userDomain: UserDomain,
    ): PostDto {
        return postService.create(userDomain.id, request)
    }

    @GetMapping
    fun getPosts(
        @RequestParam(value = "title", required = false) title: String?,
        @RequestParam(value = "category", required = false) category: String?,
        @RequestParam(value = "limit", defaultValue = "20") limit: Int,
    ): GetPostsResponse {
        require(limit in 1..100) { "Limit must be between 1 and 100" }

        val posts = postService.getPosts(title = title, category = category, limit = limit + 1)

        return GetPostsResponse(
            posts = posts.take(limit),
            cursor = if (posts.size > limit) {
                posts.take(limit).last()
                    .let { PostCursor(it.id, title, category, it.createdAt) }
                    .let { Base64ObjectMapper.toBase64(it) }
            } else
                null
        )
    }

    @GetMapping(params = ["cursor"])
    fun getPostsByCursor(
        @RequestParam cursor: String,
        @RequestParam(value = "limit", defaultValue = "20") limit: Int,
    ): GetPostsResponse {
        require(limit in 1..100) { "Limit must be between 1 and 100" }

        val postCursor = Base64ObjectMapper.fromBase64<PostCursor>(cursor)
        val posts = postService.getPosts(
            title = postCursor.title,
            category = postCursor.category,
            lastId = postCursor.id,
            lastCreatedAt = postCursor.createdAt,
            limit = limit + 1,
        )

        return GetPostsResponse(
            posts = posts.take(limit),
            cursor = if (posts.size > limit) {
                posts.take(limit).last()
                    .let { PostCursor(it.id, postCursor.title, postCursor.category, it.createdAt) }
                    .let { Base64ObjectMapper.toBase64(it) }
            } else
                null
        )
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
        @RequestBody @Valid request: UpdatePost,
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

    @PostMapping("/{id}/comments")
    fun addComment(@PathVariable id: String): CommentDto {
        TODO("")
    }
}
