package com.example.blog.controller

import com.example.blog.dto.CommentDto
import com.example.blog.dto.CreateCommentRequest
import com.example.blog.security.AccountDetail
import com.example.blog.service.CommentService
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
        @AuthenticationPrincipal accountDetail: AccountDetail
    ): CommentDto {
        return commentService.addComment(accountDetail.id, accountDetail.username, request)
    }
}