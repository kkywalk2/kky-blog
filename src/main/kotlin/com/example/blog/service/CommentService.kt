package com.example.blog.service

import com.example.blog.dto.CommentDto
import com.example.blog.dto.CreateCommentRequest
import com.example.blog.repository.CommentRepository
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val hikariDataSource: HikariDataSource
) {
    fun addComment(accountId: Long, accountName: String, request: CreateCommentRequest): CommentDto =
        transaction(Database.connect(hikariDataSource)) {
            commentRepository.save(accountId, accountName, request)
        }
}