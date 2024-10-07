package com.authorizer.adapters.out.repository.entity

import com.authorizer.application.core.domain.Transaction
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,
    val uuid: UUID,
    val accountId: Long,
    val amount: BigDecimal,
    val mcc: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?
) {
    constructor(transaction: Transaction): this(
        id = transaction.id,
        uuid = transaction.uuid!!,
        accountId = transaction.accountId,
        amount = transaction.amount,
        mcc = transaction.mcc,
        createdAt = transaction.createdAt,
        updatedAt = transaction.updatedAt,
        deletedAt = transaction.deletedAt
    )
}