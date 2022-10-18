package com.example.blog.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Posts : LongIdTable("post_entity") {
    val accountId = long("account_id")
    val views = long("views").default(0)
    val deleted = bool("deleted").default(false)
    val title = varchar("title", 255)
    val content = text("content")
    val category = varchar("category", 50)
    val createdAt = datetime("create_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}

class PostsEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PostsEntity>(Posts)

    val accountId by Posts.accountId
    val views by Posts.views
    val deleted by Posts.deleted
    val title by Posts.title
    val content by Posts.content
    val category by Posts.category
    val createdAt by Posts.createdAt
    val updatedAt by Posts.updatedAt
    val comments by CommentsEntity referrersOn Comments.postId
}