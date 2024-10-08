package com.authorizer.adapters.`in`.controller.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProcessCreditCardTransactionRequest(
    @field:NotBlank(message = "Account id can't be empty")
    val accountId: String,
    @field:NotNull(message = "Total amount can't be empty")
    val totalAmount: BigDecimal,
    @field:NotBlank(message = "Merchant category code can't be empty")
    val mcc: String,
    @field:NotBlank(message = "Merchant name can't be empty")
    val merchant: String
)
