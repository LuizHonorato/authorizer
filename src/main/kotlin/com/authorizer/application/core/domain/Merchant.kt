package com.authorizer.application.core.domain

import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import java.time.Instant
import java.util.UUID

data class Merchant(
    val id: Long,
    val uuid: UUID,
    val name: String,
    val preferredBalanceType: BalanceTypeEnum,
    val fallbackBalanceType: BalanceTypeEnum,
    val createdAt: Instant? = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
    val deletedAt: Instant? = null
)
