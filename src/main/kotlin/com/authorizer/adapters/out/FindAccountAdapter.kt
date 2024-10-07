package com.authorizer.adapters.out

import com.authorizer.adapters.out.repository.AccountRepository
import com.authorizer.application.core.domain.Account
import com.authorizer.application.ports.out.FindAccountOutputPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class FindAccountAdapter(
    private val accountRepository: AccountRepository
): FindAccountOutputPort {
    override fun findAccountByUuId(uuid: UUID): Account? {
        val accountEntity = accountRepository.findByUuid(uuid);

        if (accountEntity != null) {
            return accountEntity.toAccount()
        }

        return null
    }
}