package com.authorizer.adapters.out

import com.authorizer.adapters.out.repository.AccountRepository
import com.authorizer.adapters.out.repository.entity.AccountEntity
import com.authorizer.application.core.domain.Account
import com.authorizer.application.ports.out.UpdateAccountOutputPort
import org.springframework.stereotype.Component

@Component
class UpdateAccountAdapter(
    private val accountRepository: AccountRepository
): UpdateAccountOutputPort {
    override fun updateAccount(account: Account) {
        accountRepository.save(AccountEntity(account))
    }
}