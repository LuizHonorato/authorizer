package com.authorizer.application.core.domain

import java.time.Instant
import java.util.UUID

data class Merchant(
    val id: Long,
    val uuid: UUID,
    val name: String,
    val preferredBalanceType: BalanceType,
    val fallbackBalanceType: BalanceType,
    val createdAt: Instant? = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
    val deletedAt: Instant? = null
)
