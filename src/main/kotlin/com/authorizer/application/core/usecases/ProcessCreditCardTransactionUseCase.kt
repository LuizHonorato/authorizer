package com.authorizer.application.core.usecases

import com.authorizer.application.core.domain.Transaction
import com.authorizer.application.core.domain.enums.ProcessTransactionResponseStatusEnum
import com.authorizer.application.core.exceptions.NotFoundException
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionInput
import com.authorizer.application.ports.`in`.ProcessCreditCardTransactionInputPort
import com.authorizer.application.ports.out.CreateTransactionOutputPort
import com.authorizer.application.ports.out.FindAccountOutputPort
import com.authorizer.application.ports.out.UpdateAccountOutputPort

class ProcessCreditCardTransactionUseCase(
    private val findAccountOutputPort: FindAccountOutputPort,
    private val updateAccountOutputPort: UpdateAccountOutputPort,
    private val createTransactionOutputPort: CreateTransactionOutputPort
): ProcessCreditCardTransactionInputPort {
    override fun execute(input: ProcessCreditCardTransactionInput): String {
        val account = findAccountOutputPort.findAccountByUuId(input.accountId)
            ?: throw NotFoundException("Account with id ${input.accountId} not found")

        val transaction = Transaction(accountId = account.id, amount = input.totalAmount, mcc = input.mcc)
        val processedTransaction = transaction.processTransaction(account)

        if (processedTransaction.code == ProcessTransactionResponseStatusEnum.APPROVED.code) {
            val updatedAccount = account.updateBalanceAfterAuthorizationProcess(processedTransaction)
            updateAccountOutputPort.updateAccount(updatedAccount)
        }

        createTransactionOutputPort.createTransaction(transaction)

        return processedTransaction.code
    }
}