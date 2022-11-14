package com.example.blog.repository

import com.example.blog.config.firstOptional
import com.example.blog.dto.AccountDto
import com.example.blog.dto.SignUpRequest
import com.example.blog.entity.Accounts
import com.example.blog.entity.AccountsEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AccountRepository(
    private val passwordEncoder: PasswordEncoder
) {

    fun findByAccountName(accountName: String): Optional<AccountsEntity> {
        return AccountsEntity
            .find { Accounts.accountName eq accountName }
            .firstOptional()
    }

    fun save(request: SignUpRequest): AccountDto {
        return AccountsEntity.new {
            accountName = request.accountName
            password = passwordEncoder.encode(request.password)
            email = request.email
        }.toDto()
    }

    private fun AccountsEntity.toDto(): AccountDto {
        return AccountDto(
            name = accountName,
            email = email,
            createdAt = Optional.ofNullable(createdAt),
            updatedAt = Optional.ofNullable(updatedAt)
        )
    }
}
