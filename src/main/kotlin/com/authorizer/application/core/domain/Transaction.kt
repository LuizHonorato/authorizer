package com.authorizer.application.core.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Transaction(
    val id: Long,
    val uuid: UUID,
    val accountId: Long,
    val amount: BigDecimal,
    val mcc: String,
    val createdAt: Instant? = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
    val deletedAt: Instant? = null
) {
    fun resolveBalanceType(): BalanceType = when (mcc) {
        "5411", "5412" -> BalanceType.FOOD
        "5811", "5812" -> BalanceType.MEAL
        else -> BalanceType.CASH
    }
}
