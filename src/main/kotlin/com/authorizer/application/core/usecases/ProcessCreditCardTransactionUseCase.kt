package com.authorizer.application.core.usecases

import com.authorizer.application.core.domain.Transaction
import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import com.authorizer.application.core.domain.enums.ProcessTransactionResponseStatusEnum
import com.authorizer.application.core.exceptions.NotFoundException
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionInput
import com.authorizer.application.ports.`in`.ProcessCreditCardTransactionInputPort
import com.authorizer.application.ports.out.*

class ProcessCreditCardTransactionUseCase(
    private val findAccountOutputPort: FindAccountOutputPort,
    private val findMerchantOutputPort: FindMerchantOutputPort,
    private val findMerchantCategoryCodeOutputPort: FindMerchantCategoryCodeOutputPort,
    private val updateAccountOutputPort: UpdateAccountOutputPort,
    private val createTransactionOutputPort: CreateTransactionOutputPort
): ProcessCreditCardTransactionInputPort {
    override fun execute(input: ProcessCreditCardTransactionInput): String {
        val account = findAccountOutputPort.findAccountByUuId(input.accountId)
            ?: throw NotFoundException("Account with id ${input.accountId} not found")

        val merchantCategoryCode = findMerchantCategoryCodeOutputPort.findByMerchantCategoryCode(input.mcc)

        val balanceType: BalanceTypeEnum?

        if (merchantCategoryCode == null) {
            val merchant = findMerchantOutputPort.findByName(input.merchant)
            ?: throw NotFoundException("Merchant ${input.merchant} not found")

            balanceType = merchant.preferredBalanceType
        } else {
            balanceType = Transaction.resolveBalanceType(merchantCategoryCode.code)
        }

        val transaction = Transaction(accountId = account.id, amount = input.totalAmount, balanceType = balanceType)

        val processedTransactionOutput = transaction.processTransaction(balanceType, account)

        if (processedTransactionOutput.code == ProcessTransactionResponseStatusEnum.APPROVED.code) {
            val updatedAccount = account.withdraw(processedTransactionOutput)
            updateAccountOutputPort.updateAccount(updatedAccount)
        }

        val processedTransaction = transaction.copy(responseCode = processedTransactionOutput.code)

        createTransactionOutputPort.createTransaction(processedTransaction)

        return processedTransactionOutput.code
    }
}