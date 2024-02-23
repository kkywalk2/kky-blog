package com.example.blog.post.infrastuctures

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Comments : LongIdTable("comment_entity") {
    val accountName = varchar("account_name", 50)
    val accountId = long("account_id")
    val postId = reference("post_id", Posts)
    val content = text("content")
    val createdAt = datetime("create_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
}

class CommentDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CommentDao>(Comments)

    var accountName by Comments.accountName
    var accountId by Comments.accountId
    var postId by Comments.postId
    var content by Comments.content
    var createdAt by Comments.createdAt
    var updatedAt by Comments.updatedAt
}
