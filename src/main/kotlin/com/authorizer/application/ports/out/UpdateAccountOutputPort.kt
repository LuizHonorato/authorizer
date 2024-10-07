package com.authorizer.application.ports.out

import com.authorizer.application.core.domain.Account

interface UpdateAccountOutputPort {
    fun updateAccount(account: Account)
}