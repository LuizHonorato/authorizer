package com.authorizer.adapters.out

import com.authorizer.adapters.out.repository.MerchantRepository
import com.authorizer.application.core.domain.Merchant
import com.authorizer.application.ports.out.FindMerchantOutputPort
import org.springframework.stereotype.Component

@Component
class FindMerchantAdapter(
    private val merchantRepository: MerchantRepository
): FindMerchantOutputPort {
    override fun findByName(name: String): Merchant? {
        val merchantEntity = merchantRepository.findByName(name)

        if (merchantEntity != null) {
            return merchantEntity.toMerchant()
        }

        return null
    }

}