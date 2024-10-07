package com.authorizer.application.core.usecases.dtos

import java.math.BigDecimal
import java.util.UUID

data class ProcessCreditCardTransactionInput(
    val accountId: UUID,
    val totalAmount: BigDecimal,
    val merchant: String,
    val mcc: String
)
