package com.example.blog.service

import com.example.blog.dto.AccountDto
import com.example.blog.dto.AuthDto
import com.example.blog.dto.SignInRequest
import com.example.blog.dto.SignUpRequest
import com.example.blog.entity.AccountEntity
import com.example.blog.exception.UnauthorizedException
import com.example.blog.repository.AccountRepository
import com.example.blog.security.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {
    @Transactional
    fun createAccount(request: SignUpRequest): AccountDto {
        val accountEntity = accountRepository.save(request.toEntity())
        return accountEntity.toDto()
    }

    fun accountValidation(request: SignInRequest): AuthDto {
        val account = accountRepository.findByAccountName(request.accountName)
        if (!passwordEncoder.matches(request.password, account.password)) throw UnauthorizedException()
        return AuthDto(account.accountName, jwtUtil.createToken(account.accountName))
    }

    private fun SignUpRequest.toEntity(): AccountEntity {
        return AccountEntity(
            accountName = accountName,
            password = passwordEncoder.encode(password),
            email = email
        )
    }

    private fun AccountEntity.toDto(): AccountDto {
        return AccountDto(
            name = accountName,
            email = email,
            createAt = Optional.ofNullable(createAt),
            updateAt = Optional.ofNullable(updatedAt)
        )
    }
}