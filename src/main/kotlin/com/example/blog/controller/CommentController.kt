package com.example.blog.controller

import com.example.blog.dto.CommentDto
import com.example.blog.dto.CreateCommentRequest
import com.example.blog.service.CommentService
import com.example.blog.user.domains.UserDomain
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addComment(
        @RequestBody request: CreateCommentRequest,
        @AuthenticationPrincipal userDomain: UserDomain,
    ): CommentDto {
        return commentService.addComment(userDomain.id, userDomain.username, request)
    }
}
