package com.example.blog.repository

import com.example.blog.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : JpaRepository<AccountEntity, Long> {
    fun findByAccountName(accountName: String): Optional<AccountEntity>
}
