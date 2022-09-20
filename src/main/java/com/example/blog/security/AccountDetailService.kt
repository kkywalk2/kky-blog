package com.example.blog.security

import com.example.blog.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountDetailService (
    private val accountRepository: AccountRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(accountName: String): AccountDetail {
        val account = accountRepository
            .findByAccountName(accountName)
            .orElseThrow { UsernameNotFoundException("user name not found!") }

        return AccountDetail(account)
    }
}