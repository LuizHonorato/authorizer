package com.authorizer.infrastructure.container

import com.authorizer.adapters.out.CreateTransactionAdapter
import com.authorizer.adapters.out.FindAccountAdapter
import com.authorizer.adapters.out.UpdateAccountAdapter
import com.authorizer.application.core.usecases.ProcessCreditCardTransactionUseCase
import com.authorizer.application.ports.`in`.ProcessCreditCardTransactionInputPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProcessCreditCardTransactionDIConfig {
    @Bean
    fun findAccountDependencyInjectionConfig(
        findAccountAdapter: FindAccountAdapter,
        updateAccountAdapter: UpdateAccountAdapter,
        createTransactionAdapter: CreateTransactionAdapter
    ): ProcessCreditCardTransactionInputPort =
        ProcessCreditCardTransactionUseCase(findAccountAdapter, updateAccountAdapter, createTransactionAdapter)
}