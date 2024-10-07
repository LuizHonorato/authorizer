package com.authorizer.adapters.out.repository

import com.authorizer.adapters.out.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository: JpaRepository<TransactionEntity, Long>