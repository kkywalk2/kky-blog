package com.example.blog.service

import com.example.blog.dto.AccountDto
import com.example.blog.dto.AuthDto
import com.example.blog.dto.SignInRequest
import com.example.blog.dto.SignUpRequest
import com.example.blog.exception.UnauthorizedException
import com.example.blog.repository.AccountRepository
import com.example.blog.security.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    fun createAccount(request: SignUpRequest): AccountDto {
        return accountRepository.save(request)
    }

    fun accountValidation(request: SignInRequest): AuthDto {
        val account = accountRepository
            .findByAccountName(request.accountName)
            .filter { passwordEncoder.matches(request.password, it.password) }
            .orElseThrow { UnauthorizedException() }

        return AuthDto(account.accountName, jwtUtil.createToken(account.accountName))
    }
}
