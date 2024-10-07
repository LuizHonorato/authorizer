package com.authorizer.adapters.out

import com.authorizer.adapters.out.repository.TransactionRepository
import com.authorizer.adapters.out.repository.entity.TransactionEntity
import com.authorizer.application.core.domain.Transaction
import com.authorizer.application.ports.out.CreateTransactionOutputPort

class CreateTransactionAdapter(
    private val transactionRepository: TransactionRepository
): CreateTransactionOutputPort {
    override fun createTransaction(transaction: Transaction) {
        transactionRepository.save(TransactionEntity(transaction))
    }
}