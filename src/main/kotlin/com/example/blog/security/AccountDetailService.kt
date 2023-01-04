package com.example.blog.security

import com.example.blog.entity.Accounts
import com.example.blog.entity.Account
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountDetailService : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(accountName: String): AccountDetail {
        val account = Account
            .find { Accounts.accountName eq accountName }
            .first()

        return AccountDetail(account)
    }
}