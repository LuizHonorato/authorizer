package com.authorizer.application.core.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Account(
    val id: Long,
    val uuid: UUID,
    val foodBalance: BigDecimal,
    val mealBalance: BigDecimal,
    val cashBalance: BigDecimal,
    val createdAt: Instant? = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
    val deletedAt: Instant? = null
)
