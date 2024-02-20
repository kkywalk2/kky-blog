package com.example.blog.user.domains

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class UserDomain(
    val id: Long = 0,
    val name: String,
    val encodedPassword: String,
    val email: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) : UserDetails {

    fun validatePassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        val result =  passwordEncoder.matches(password, this.encodedPassword)
        return result
    }

    fun toClaims(): Map<String, String> {
        return mapOf(
            "email" to email,
            "id" to id.toString(),
            "name" to name,
            "createdAt" to createdAt.toString(),
            "updatedAt" to updatedAt.toString(),
        )
    }

    companion object {
        fun createUser(
            createUser: CreateUser,
            passwordEncoder: PasswordEncoder,
            now: LocalDateTime = LocalDateTime.now()
        ): UserDomain {
            return UserDomain(
                name = createUser.name,
                encodedPassword = passwordEncoder.encode(createUser.password),
                email = createUser.email,
                createdAt = now,
                updatedAt = now,
            )
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return encodedPassword
    }

    override fun getUsername(): String {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}
