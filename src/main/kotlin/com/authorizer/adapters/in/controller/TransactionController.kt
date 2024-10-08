package com.authorizer.adapters.`in`.controller

import com.authorizer.adapters.`in`.controller.request.ProcessCreditCardTransactionRequest
import com.authorizer.adapters.`in`.controller.response.ProcessCreditCardTransactionResponse
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionInput
import com.authorizer.application.ports.`in`.ProcessCreditCardTransactionInputPort
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(
    private val processCreditCardTransactionInputPort: ProcessCreditCardTransactionInputPort
) {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun processTransaction(@Valid @RequestBody processCreditCardTransactionRequest: ProcessCreditCardTransactionRequest): ProcessCreditCardTransactionResponse {
        val responseCode = processCreditCardTransactionInputPort.execute(ProcessCreditCardTransactionInput(
            accountId = UUID.fromString(processCreditCardTransactionRequest.accountId),
            totalAmount = processCreditCardTransactionRequest.totalAmount,
            mcc = processCreditCardTransactionRequest.mcc,
            merchant = processCreditCardTransactionRequest.merchant
        ))

        return ProcessCreditCardTransactionResponse(responseCode)
    }
}