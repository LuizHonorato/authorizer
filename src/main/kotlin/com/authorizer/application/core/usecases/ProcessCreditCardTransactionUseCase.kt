package com.authorizer.application.core.usecases

import com.authorizer.application.core.domain.Account
import com.authorizer.application.core.domain.Transaction
import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import com.authorizer.application.core.domain.enums.ProcessTransactionResponseStatusEnum
import com.authorizer.application.core.exceptions.NotFoundException
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionInput
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionOutput
import com.authorizer.application.ports.`in`.ProcessCreditCardTransactionInputPort
import com.authorizer.application.ports.out.*
import java.util.UUID

class ProcessCreditCardTransactionUseCase(
    private val findAccountOutputPort: FindAccountOutputPort,
    private val findMerchantOutputPort: FindMerchantOutputPort,
    private val findMerchantCategoryCodeOutputPort: FindMerchantCategoryCodeOutputPort,
    private val updateAccountOutputPort: UpdateAccountOutputPort,
    private val createTransactionOutputPort: CreateTransactionOutputPort
): ProcessCreditCardTransactionInputPort {

    override fun execute(input: ProcessCreditCardTransactionInput): String {
        val account = findAccountById(input.accountId)
        val balanceType = resolveBalanceType(input)

        val transaction = Transaction(accountId = account.id, amount = input.totalAmount, balanceType = balanceType)
        val processedTransactionOutput = processTransaction(transaction, account)

        if (processedTransactionOutput.code == ProcessTransactionResponseStatusEnum.APPROVED.code) {
            val updatedAccount = account.withdraw(processedTransactionOutput)
            updateAccountOutputPort.updateAccount(updatedAccount)
        }

        createTransactionOutputPort.createTransaction(transaction.copy(responseCode = processedTransactionOutput.code))
        return processedTransactionOutput.code
    }

    private fun findAccountById(accountId: UUID): Account =
        findAccountOutputPort.findAccountByUuId(accountId) ?: throw NotFoundException("Account with id $accountId not found")

    private fun resolveBalanceType(input: ProcessCreditCardTransactionInput): BalanceTypeEnum {
        val merchantCategoryCode = findMerchantCategoryCodeOutputPort.findByMerchantCategoryCode(input.mcc)

        return if (merchantCategoryCode != null) {
            Transaction.resolveBalanceType(merchantCategoryCode.code)
        } else {
            val merchant = findMerchantOutputPort.findByName(input.merchant)
                ?: throw NotFoundException("Merchant ${input.merchant} not found")
            merchant.preferredBalanceType
        }
    }

    private fun processTransaction(transaction: Transaction, account: Account): ProcessCreditCardTransactionOutput =
        transaction.processTransaction(transaction.balanceType, account)
}
