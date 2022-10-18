package com.example.blog.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime

object Comments : LongIdTable("comment_entity") {
    val accountName = varchar("account_name", 50)
    val accountId = long("account_id")
    val postId = reference("post_id", Posts)
    val content = text("content")
    val createdAt = datetime("create_at").defaultExpression(CurrentTimestamp())
    val updatedAt = datetime("updated_at").defaultExpression(CurrentTimestamp())
}

class CommentsEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CommentsEntity>(Comments)

    val accountName by Comments.accountName
    val accountId by Comments.accountId
    val postId by Comments.postId
    val content by Comments.content
    val createdAt by Comments.createdAt
    val updatedAt by Comments.updatedAt
}