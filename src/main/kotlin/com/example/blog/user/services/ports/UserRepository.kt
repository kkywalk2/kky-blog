package com.example.blog.user.services.ports

import com.example.blog.user.domains.UserDomain
import java.util.Optional

interface UserRepository {

    fun findByUserName(name: String): Optional<UserDomain>

    fun save(userDomain: UserDomain): UserDomain

}
