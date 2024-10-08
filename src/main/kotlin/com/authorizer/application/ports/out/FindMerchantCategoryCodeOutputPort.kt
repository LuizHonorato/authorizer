package com.authorizer.application.ports.out

import com.authorizer.application.core.domain.MerchantCategoryCode

interface FindMerchantCategoryCodeOutputPort {
    fun findByMerchantCategoryCode(code: String): MerchantCategoryCode?
}