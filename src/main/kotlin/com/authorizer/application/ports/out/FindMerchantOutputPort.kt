package com.authorizer.application.ports.out

import com.authorizer.application.core.domain.Merchant

interface FindMerchantOutputPort {
    fun findByName(name: String): Merchant?
}