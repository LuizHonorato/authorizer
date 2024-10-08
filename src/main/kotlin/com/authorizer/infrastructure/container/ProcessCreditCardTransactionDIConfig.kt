package com.authorizer.infrastructure.container

import com.authorizer.adapters.out.*
import com.authorizer.application.core.usecases.ProcessCreditCardTransactionUseCase
import com.authorizer.application.ports.`in`.ProcessCreditCardTransactionInputPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProcessCreditCardTransactionDIConfig {
    @Bean
    fun processCreditCardDependencyInjectionConfig(
        findAccountAdapter: FindAccountAdapter,
        findMerchantAdapter: FindMerchantAdapter,
        findMerchantCategoryCodeAdapter: FindMerchantCategoryCodeAdapter,
        updateAccountAdapter: UpdateAccountAdapter,
        createTransactionAdapter: CreateTransactionAdapter
    ): ProcessCreditCardTransactionInputPort =
        ProcessCreditCardTransactionUseCase(
            findAccountAdapter,
            findMerchantAdapter,
            findMerchantCategoryCodeAdapter,
            updateAccountAdapter,
            createTransactionAdapter
        )
}