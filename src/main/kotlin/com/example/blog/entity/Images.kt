package com.example.blog.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime

object Images : LongIdTable("image_entity") {
    val originalName = varchar("original_name", 255)
    val filePath = varchar("file_path", 255)
    val createdAt = datetime("create_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
}

class ImagesEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ImagesEntity>(Images)

    var originalName by Images.originalName
    var filePath by Images.filePath
    var createdAt by Images.createdAt
    var updatedAt by Images.updatedAt

    fun getPath(): Path {
        return Paths.get(filePath)
    }
}