package com.example.blog.user.infrastructures

import com.example.blog.config.firstOptional
import com.example.blog.user.domains.UserDomain
import com.example.blog.user.services.ports.UserRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl: UserRepository {

    override fun findByUserName(name: String): Optional<UserDomain> {
        return UserDao
            .find { Users.name eq name }
            .firstOptional()
            .map { it.toDomain() }
    }

    override fun save(userDomain: UserDomain): UserDomain {
        return UserDao.new {
            name = userDomain.name
            password = userDomain.encodedPassword
            email = userDomain.email
        }.toDomain()
    }

    fun UserDao.toDomain(): UserDomain {
        return UserDomain(
            id = id.value,
            name = name,
            encodedPassword = password,
            email = email,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

}
