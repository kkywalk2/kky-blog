package com.example.blog.security

import com.example.blog.entity.Accounts
import com.example.blog.entity.AccountsEntity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountDetailService : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(accountName: String): AccountDetail {
        val account = AccountsEntity
            .find { Accounts.accountName eq accountName }
            .first()

        return AccountDetail(account)
    }
}