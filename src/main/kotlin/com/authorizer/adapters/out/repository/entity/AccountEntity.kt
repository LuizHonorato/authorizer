package com.authorizer.adapters.out.repository.entity

import com.authorizer.application.core.domain.Account
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "accounts_id_seq", sequenceName = "accounts_id_seq", allocationSize = 1)
    val id: Long,
    val uuid: UUID,
    val merchantId: Long,
    val foodBalance: BigDecimal,
    val mealBalance: BigDecimal,
    val cashBalance: BigDecimal,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?
) {
    constructor(account: Account): this(
        id = account.id,
        uuid = account.uuid,
        merchantId = account.merchantId,
        foodBalance = account.foodBalance,
        mealBalance = account.mealBalance,
        cashBalance = account.cashBalance,
        createdAt = account.createdAt,
        updatedAt = account.updatedAt,
        deletedAt = account.deletedAt
    )

    fun toAccount(): Account = Account(id, uuid, merchantId, foodBalance, mealBalance, cashBalance, createdAt, updatedAt, deletedAt)
}