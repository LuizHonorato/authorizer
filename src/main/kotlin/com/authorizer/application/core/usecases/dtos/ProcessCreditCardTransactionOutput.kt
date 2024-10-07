package com.authorizer.application.core.usecases.dtos

import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import java.math.BigDecimal

data class ProcessCreditCardTransactionOutput(
    val code: String,
    val type: BalanceTypeEnum,
    val finalAmount: BigDecimal,
)
