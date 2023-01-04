package com.example.blog.entity

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Accounts : LongIdTable("account_entity") {
    val accountName = varchar("account_name", 255)
    val password = varchar("password", 255)
    val email = varchar("email", 255)
    val createdAt = datetime("create_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").defaultExpression(CurrentTimestamp()).clientDefault { LocalDateTime.now() }
}

class Account(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Account>(Accounts)

    var accountName by Accounts.accountName
    var password by Accounts.password
    var email by Accounts.email
    var createdAt by Accounts.createdAt
    var updatedAt by Accounts.updatedAt
}