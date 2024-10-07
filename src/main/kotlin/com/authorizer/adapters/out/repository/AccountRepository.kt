package com.authorizer.adapters.out.repository

import com.authorizer.adapters.out.repository.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository: JpaRepository<AccountEntity, Long> {
    fun findByUuid(uuid: UUID): AccountEntity?
}