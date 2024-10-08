package com.authorizer.adapters.out.repository

import com.authorizer.adapters.out.repository.entity.MerchantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MerchantRepository: JpaRepository<MerchantEntity, Long> {
    fun findByName(name: String): MerchantEntity?
}