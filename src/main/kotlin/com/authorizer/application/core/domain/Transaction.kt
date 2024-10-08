package com.authorizer.application.core.domain

import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import com.authorizer.application.core.domain.enums.ProcessTransactionResponseStatusEnum
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionOutput
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

class Transaction(
    val id: Long? = null,
    val uuid: UUID? = UUID.randomUUID(),
    val accountId: Long,
    val amount: BigDecimal,
    val mcc: String,
    val createdAt: Instant? = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
    val deletedAt: Instant? = null
) {
    fun resolveBalanceType(): BalanceTypeEnum = when (mcc) {
        "5411", "5412" -> BalanceTypeEnum.FOOD
        "5811", "5812" -> BalanceTypeEnum.MEAL
        else -> BalanceTypeEnum.CASH
    }

    fun processTransaction(account: Account): ProcessCreditCardTransactionOutput =
        hasSufficientBalance(resolveBalanceType(), amount, account);

    private fun hasSufficientBalance(type: BalanceTypeEnum, amount: BigDecimal, account: Account): ProcessCreditCardTransactionOutput {
        val balance = when (type) {
            BalanceTypeEnum.FOOD -> account.foodBalance
            BalanceTypeEnum.MEAL -> account.mealBalance
            BalanceTypeEnum.CASH -> account.cashBalance
        }

        if (balance >= amount) {
            return ProcessCreditCardTransactionOutput(
                ProcessTransactionResponseStatusEnum.APPROVED.code,
                type,
                amount
            )
        }

        if (type != BalanceTypeEnum.CASH) {
            if (account.cashBalance >= amount) {
                return ProcessCreditCardTransactionOutput(
                    ProcessTransactionResponseStatusEnum.APPROVED.code,
                    BalanceTypeEnum.CASH,
                    amount,
                )
            }
        }

        return ProcessCreditCardTransactionOutput(
            ProcessTransactionResponseStatusEnum.REJECTED_BY_INSUFFICIENT_BALANCE.code,
            type,
            amount
        )
    }
}
