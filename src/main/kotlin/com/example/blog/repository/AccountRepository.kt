package com.example.blog.repository

import com.example.blog.config.firstOptional
import com.example.blog.dto.AccountDto
import com.example.blog.dto.SignUpRequest
import com.example.blog.entity.Accounts
import com.example.blog.entity.Account
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AccountRepository(
    private val passwordEncoder: PasswordEncoder
) {

    fun findByAccountName(accountName: String): Optional<Account> {
        return Account
            .find { Accounts.accountName eq accountName }
            .firstOptional()
    }

    fun save(request: SignUpRequest): AccountDto {
        return Account.new {
            accountName = request.accountName
            password = passwordEncoder.encode(request.password)
            email = request.email
        }.toDto()
    }

    private fun Account.toDto(): AccountDto {
        return AccountDto(
            name = accountName,
            email = email,
            createdAt = Optional.ofNullable(createdAt),
            updatedAt = Optional.ofNullable(updatedAt)
        )
    }
}
