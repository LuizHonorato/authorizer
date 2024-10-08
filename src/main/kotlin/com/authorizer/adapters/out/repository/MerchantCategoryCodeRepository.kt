package com.authorizer.adapters.out.repository

import com.authorizer.adapters.out.repository.entity.MerchantCategoryCodeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantCategoryCodeRepository: JpaRepository<MerchantCategoryCodeEntity, Long> {
    fun findByCode(code: String): MerchantCategoryCodeEntity?
}