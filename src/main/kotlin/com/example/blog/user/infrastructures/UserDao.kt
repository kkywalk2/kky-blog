package com.example.blog.user.infrastructures

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Users : LongIdTable("account_entity") {
    val name = varchar("account_name", 255)
    val password = varchar("password", 255)
    val email = varchar("email", 255)
    val createdAt = datetime("create_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
}

class UserDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserDao>(Users)

    var name by Users.name
    var password by Users.password
    var email by Users.email
    var createdAt by Users.createdAt
    var updatedAt by Users.updatedAt
}
