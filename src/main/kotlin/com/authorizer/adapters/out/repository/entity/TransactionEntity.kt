package com.authorizer.adapters.out.repository.entity

import com.authorizer.application.core.domain.Transaction
import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "transactions_id_seq", sequenceName = "transactions_id_seq", allocationSize = 1)
    val id: Long?,
    val uuid: UUID,
    val accountId: Long,
    val amount: BigDecimal,
    val balanceType: BalanceTypeEnum,
    val responseCode: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?
) {
    constructor(transaction: Transaction): this(
        id = transaction.id,
        uuid = transaction.uuid!!,
        accountId = transaction.accountId,
        amount = transaction.amount,
        balanceType = transaction.balanceType,
        responseCode = transaction.responseCode!!,
        createdAt = transaction.createdAt,
        updatedAt = transaction.updatedAt,
        deletedAt = transaction.deletedAt
    )
}