package com.authorizer.application.core.domain

import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionOutput
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
) {
    fun updateBalanceAfterAuthorizationProcess(processCreditCardTransactionOutput: ProcessCreditCardTransactionOutput): Account =
        when (processCreditCardTransactionOutput.type) {
            BalanceTypeEnum.FOOD -> this.copy(foodBalance = processCreditCardTransactionOutput.finalAmount, updatedAt = Instant.now())
            BalanceTypeEnum.MEAL -> this.copy(mealBalance = processCreditCardTransactionOutput.finalAmount, updatedAt = Instant.now())
            BalanceTypeEnum.CASH -> this.copy(cashBalance = processCreditCardTransactionOutput.finalAmount, updatedAt = Instant.now())
        }
}
