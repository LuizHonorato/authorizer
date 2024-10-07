package com.authorizer.application.core.domain.enums

enum class ProcessTransactionResponseStatusEnum(val code: String) {
    APPROVED("00"),
    REJECTED_BY_INSUFFICIENT_BALANCE("51"),
    REJECTED_BY_INTERNAL_ERROR("07")
}