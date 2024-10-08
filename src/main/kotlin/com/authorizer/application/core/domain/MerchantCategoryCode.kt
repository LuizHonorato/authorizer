package com.authorizer.application.core.domain

import java.time.Instant
import java.util.*

data class MerchantCategoryCode(
    val id: Long? = null,
    val uuid: UUID? = UUID.randomUUID(),
    val code: String,
    val createdAt: Instant? = Instant.now(),
    val updatedAt: Instant? = Instant.now(),
    val deletedAt: Instant? = null
)
