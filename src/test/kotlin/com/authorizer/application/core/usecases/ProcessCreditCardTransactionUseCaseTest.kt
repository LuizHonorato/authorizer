package com.authorizer.application.core.usecases

import com.authorizer.application.core.domain.Account
import com.authorizer.application.core.domain.Merchant
import com.authorizer.application.core.domain.MerchantCategoryCode
import com.authorizer.application.core.domain.enums.BalanceTypeEnum
import com.authorizer.application.core.domain.enums.ProcessTransactionResponseStatusEnum
import com.authorizer.application.core.usecases.dtos.ProcessCreditCardTransactionInput
import com.authorizer.application.ports.out.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.util.*

class ProcessCreditCardTransactionUseCaseTest {
    private lateinit var findAccountOutputPort: FindAccountOutputPort
    private lateinit var findMerchantOutputPort: FindMerchantOutputPort
    private lateinit var findMerchantCategoryCodeOutputPort: FindMerchantCategoryCodeOutputPort
    private lateinit var updateAccountOutputPort: UpdateAccountOutputPort
    private lateinit var createTransactionOutputPort: CreateTransactionOutputPort
    private lateinit var useCase: ProcessCreditCardTransactionUseCase

    @BeforeEach
    fun setUp() {
        findAccountOutputPort = mock()
        findMerchantOutputPort = mock()
        findMerchantCategoryCodeOutputPort = mock()
        updateAccountOutputPort = mock()
        createTransactionOutputPort = mock()

        useCase = ProcessCreditCardTransactionUseCase(
            findAccountOutputPort,
            findMerchantOutputPort,
            findMerchantCategoryCodeOutputPort,
            updateAccountOutputPort,
            createTransactionOutputPort
        )
    }

    @Test
    fun `should throw NotFoundException when account is not found`() {
        // Arrange
        val uuid = UUID.randomUUID()
        val input = ProcessCreditCardTransactionInput(accountId = uuid, totalAmount = BigDecimal(100.00), mcc = "1234", merchant = "Test Merchant")
        `when`(findAccountOutputPort.findAccountByUuId(uuid)).thenReturn(null)

        // Act
        val result = useCase.execute(input)

        // Assert
        assertEquals(ProcessTransactionResponseStatusEnum.REJECTED_BY_INTERNAL_ERROR.code, result)
    }

    @Test
    fun `should process transaction and approve it`() {
        // Arrange
        val uuid = UUID.randomUUID()
        val account = Account(id = 1, uuid = uuid, merchantId = 1, foodBalance = BigDecimal(700.00), cashBalance = BigDecimal(200.00), mealBalance = BigDecimal(100.00))
        val input = ProcessCreditCardTransactionInput(accountId = account.uuid, totalAmount = BigDecimal(100.0), mcc = "5411", merchant = "Test Merchant")
        val merchantCategoryCode = MerchantCategoryCode(id = 1, code = input.mcc)

        `when`(findAccountOutputPort.findAccountByUuId(account.uuid)).thenReturn(account)
        `when`(findMerchantCategoryCodeOutputPort.findByMerchantCategoryCode(input.mcc)).thenReturn(merchantCategoryCode)

        // Act
        val result = useCase.execute(input)

        // Assert
        assertEquals(ProcessTransactionResponseStatusEnum.APPROVED.code, result)
    }

    @Test
    fun `should process transaction and denied it`() {
        // Arrange
        val uuid = UUID.randomUUID()
        val account = Account(id = 1, uuid = uuid, merchantId = 1, foodBalance = BigDecimal(700.00), cashBalance = BigDecimal(200.00), mealBalance = BigDecimal(100.00))
        val input = ProcessCreditCardTransactionInput(accountId = account.uuid, totalAmount = BigDecimal(800.0), mcc = "5411", merchant = "Test Merchant")
        val merchantCategoryCode = MerchantCategoryCode(id = 1, code = input.mcc)

        `when`(findAccountOutputPort.findAccountByUuId(account.uuid)).thenReturn(account)
        `when`(findMerchantCategoryCodeOutputPort.findByMerchantCategoryCode(input.mcc)).thenReturn(merchantCategoryCode)

        // Act
        val result = useCase.execute(input)

        // Assert
        assertEquals(ProcessTransactionResponseStatusEnum.REJECTED_BY_INSUFFICIENT_BALANCE.code, result)
    }

    @Test
    fun `should replace the mcc code by merchant config, process transaction and approve it`() {
        // Arrange
        val uuid = UUID.randomUUID()
        val account = Account(id = 1, uuid = uuid, merchantId = 1, foodBalance = BigDecimal(700.00), cashBalance = BigDecimal(200.00), mealBalance = BigDecimal(100.00))
        val input = ProcessCreditCardTransactionInput(accountId = account.uuid, totalAmount = BigDecimal(200.0), mcc = "7020", merchant = "Mercado do Zé")
        val merchant = Merchant(id = 1, uuid = UUID.randomUUID(), name = "Mercado do Zé", preferredBalanceType = BalanceTypeEnum.FOOD, fallbackBalanceType = BalanceTypeEnum.CASH)

        `when`(findAccountOutputPort.findAccountByUuId(account.uuid)).thenReturn(account)
        `when`(findMerchantCategoryCodeOutputPort.findByMerchantCategoryCode(input.mcc)).thenReturn(null)
        `when`(findMerchantOutputPort.findByName(merchant.name)).thenReturn(merchant)

        // Act
        val result = useCase.execute(input)

        // Assert
        assertEquals(ProcessTransactionResponseStatusEnum.APPROVED.code, result)
    }

    @Test
    fun `should return a failure error code when the request fails`() {
        // Arrange
        val uuid = UUID.randomUUID()
        val account = Account(id = 1, uuid = uuid, merchantId = 1, foodBalance = BigDecimal(700.00), cashBalance = BigDecimal(200.00), mealBalance = BigDecimal(100.00))
        val input = ProcessCreditCardTransactionInput(accountId = account.uuid, totalAmount = BigDecimal(200.0), mcc = "7020", merchant = "Mercado do Zé")

        `when`(findAccountOutputPort.findAccountByUuId(account.uuid)).thenThrow()

        // Act
        val result = useCase.execute(input)

        // Assert
        assertEquals(ProcessTransactionResponseStatusEnum.REJECTED_BY_INTERNAL_ERROR.code, result)
    }

    @Test
    fun `should throw NotFoundException when merchant is not found`() {
        // Arrange
        val uuid = UUID.randomUUID()
        val merchantUuid = UUID.randomUUID()
        val account = Account(id = 1, uuid = uuid, merchantId = 1, foodBalance = BigDecimal(700.00), cashBalance = BigDecimal(200.00), mealBalance = BigDecimal(100.00))
        val input = ProcessCreditCardTransactionInput(accountId = account.uuid, totalAmount = BigDecimal(200.0), mcc = "7020", merchant = "Mercado do Zé")
        val merchant = Merchant(id = 1, uuid = merchantUuid, name = "Mercado do Zé", preferredBalanceType = BalanceTypeEnum.FOOD, fallbackBalanceType = BalanceTypeEnum.CASH)

        `when`(findAccountOutputPort.findAccountByUuId(account.uuid)).thenReturn(account)
        `when`(findMerchantCategoryCodeOutputPort.findByMerchantCategoryCode(input.mcc)).thenReturn(null)
        `when`(findMerchantOutputPort.findByName(merchant.name)).thenReturn(null)

        // Act
        val result = useCase.execute(input)

        // Assert
        assertEquals(ProcessTransactionResponseStatusEnum.REJECTED_BY_INTERNAL_ERROR.code, result)
    }
}