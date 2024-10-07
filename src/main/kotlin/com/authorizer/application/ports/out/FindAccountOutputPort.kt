package com.authorizer.application.ports.out

import com.authorizer.application.core.domain.Account
import java.util.*

interface FindAccountOutputPort {
    fun findAccountById(id: UUID): Account?
}