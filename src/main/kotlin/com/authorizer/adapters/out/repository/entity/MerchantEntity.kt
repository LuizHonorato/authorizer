package com.authorizer.adapters.out.repository.entity

import com.authorizer.application.core.domain.Merchant
import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "merchants")
data class MerchantEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "merchants_id_seq", sequenceName = "merchants_id_seq", allocationSize = 1)
    val id: Long,
    val uuid: UUID,
    val name: String,
    val preferredBalanceType: String,
    val fallbackBalanceType: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?
) {
    fun toMerchant(): Merchant = Merchant(id, uuid, name, BalanceTypeEnum.valueOf(preferredBalanceType), BalanceTypeEnum.valueOf(fallbackBalanceType), createdAt, updatedAt, deletedAt)
}
