package com.authorizer.application.ports.`in`

import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionInput

interface ProcessCreditCardTransactionInputPort {
    fun execute(input: ProcessCreditCardTransactionInput): String
}