package com.guide.rest.repository

import com.guide.rest.model.entity.TransactionEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TransferRepository: CrudRepository<TransactionEntity, UUID>
