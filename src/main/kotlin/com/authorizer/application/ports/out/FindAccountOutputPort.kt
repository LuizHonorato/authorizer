package com.authorizer.application.ports.out

import com.authorizer.application.core.domain.Account
import java.util.*

interface FindAccountOutputPort {
    fun findAccountByUuId(uuid: UUID): Account?
}