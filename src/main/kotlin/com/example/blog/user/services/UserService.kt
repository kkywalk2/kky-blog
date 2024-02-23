package com.example.blog.user.services

import com.example.blog.core.TokenProvider
import com.example.blog.dto.UserDto
import com.example.blog.dto.AuthDto
import com.example.blog.dto.SignInRequest
import com.example.blog.exception.UnauthorizedException
import com.example.blog.user.domains.CreateUser
import com.example.blog.user.domains.UserDomain
import com.example.blog.user.services.ports.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
) {

    fun createAccount(request: CreateUser): UserDto {
        val user = UserDomain.createUser(request, passwordEncoder)
        return userRepository.save(user).toDto()
    }

    fun createToken(request: SignInRequest, now: LocalDateTime): AuthDto {
        return userRepository
            .findByUserName(request.name)
            .filter { it.validatePassword(request.password, passwordEncoder) }
            .map { AuthDto(it.name, tokenProvider.createToken(it.toClaims(), now)) }
            .orElseThrow { UnauthorizedException() }
    }

    private fun UserDomain.toDto(): UserDto {
        return UserDto(
            name = name,
            email = email,
            createdAt = Optional.ofNullable(createdAt),
            updatedAt = Optional.ofNullable(updatedAt)
        )
    }

}
