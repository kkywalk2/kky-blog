package com.example.blog.post.infrastuctures

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Posts : LongIdTable("post_entity") {
    val userId = long("account_id")
    val views = long("views").default(0)
    val deleted = bool("deleted").default(false)
    val title = varchar("title", 255)
    val content = text("content")
    val category = varchar("category", 50)
    val createdAt = datetime("create_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
}

class PostDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PostDao>(Posts)

    var userId by Posts.userId
    var views by Posts.views
    var deleted by Posts.deleted
    var title by Posts.title
    var content by Posts.content
    var category by Posts.category
    var createdAt by Posts.createdAt
    var updatedAt by Posts.updatedAt
    val comments by CommentDao referrersOn Comments.postId

    override fun delete() {
        deleted = true
    }
}
