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
    val id: Long,
    val uuid: UUID,
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
        foodBalance = account.foodBalance,
        mealBalance = account.mealBalance,
        cashBalance = account.cashBalance,
        createdAt = account.createdAt,
        updatedAt = account.updatedAt,
        deletedAt = account.deletedAt
    )

    fun toAccount(): Account = Account(id, uuid, foodBalance, mealBalance, cashBalance, createdAt, updatedAt, deletedAt)
}