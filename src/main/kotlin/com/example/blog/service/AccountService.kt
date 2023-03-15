package com.example.blog.service

import com.example.blog.dto.AccountDto
import com.example.blog.dto.AuthDto
import com.example.blog.dto.SignInRequest
import com.example.blog.dto.SignUpRequest
import com.example.blog.entity.Account
import com.example.blog.exception.UnauthorizedException
import com.example.blog.repository.AccountRepository
import com.example.blog.security.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    fun createAccount(request: SignUpRequest): AccountDto {
        return accountRepository.save(request).toDto()
    }

    fun accountValidation(request: SignInRequest): AuthDto {
        val account = accountRepository
            .findByAccountName(request.accountName)
            .filter { passwordEncoder.matches(request.password, it.password) }
            .orElseThrow { UnauthorizedException() }

        return AuthDto(account.accountName, jwtUtil.createToken(account.accountName))
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
