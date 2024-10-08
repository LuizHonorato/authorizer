package com.authorizer.adapters.out

import com.authorizer.adapters.out.repository.MerchantCategoryCodeRepository
import com.authorizer.application.core.domain.MerchantCategoryCode
import com.authorizer.application.ports.out.FindMerchantCategoryCodeOutputPort
import org.springframework.stereotype.Component

@Component
class FindMerchantCategoryCodeAdapter(
    private val merchantCategoryCodeRepository: MerchantCategoryCodeRepository
): FindMerchantCategoryCodeOutputPort {
    override fun findByMerchantCategoryCode(code: String): MerchantCategoryCode? {
        val merchantCategoryCodeEntity = merchantCategoryCodeRepository.findByCode(code)

        if (merchantCategoryCodeEntity != null) {
            return merchantCategoryCodeEntity.toMerchantCategoryCode()
        }

        return null
    }

}