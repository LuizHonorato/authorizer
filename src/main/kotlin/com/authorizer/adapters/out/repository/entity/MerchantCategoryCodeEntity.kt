package com.authorizer.adapters.out.repository.entity

import com.authorizer.application.core.domain.MerchantCategoryCode
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "merchants_category_codes")
data class MerchantCategoryCodeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "merchants_category_codes_id_seq", sequenceName = "merchants_category_codes_id_seq", allocationSize = 1)
    val id: Long?,
    val uuid: UUID,
    val code: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val deletedAt: Instant?
) {
    fun toMerchantCategoryCode(): MerchantCategoryCode = MerchantCategoryCode(id, uuid, code, createdAt, updatedAt, deletedAt)
}
