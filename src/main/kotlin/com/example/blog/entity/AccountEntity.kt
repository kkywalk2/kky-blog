package com.example.blog.entity

import com.example.blog.dto.AccountDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "account_name", unique = true)
    val accountName: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "email")
    val email: String,

    @CreationTimestamp
    @Column(name = "create_at")
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null,
) {
    fun toDto(): AccountDto {
        return AccountDto(
            name = accountName,
            email = email,
            createdAt = Optional.ofNullable(createdAt),
            updatedAt = Optional.ofNullable(updatedAt)
        )
    }
}
