package com.authorizer.application.ports.out

import com.authorizer.application.core.domain.Transaction

interface CreateTransactionOutputPort {
    fun createTransaction(transaction: Transaction)
}