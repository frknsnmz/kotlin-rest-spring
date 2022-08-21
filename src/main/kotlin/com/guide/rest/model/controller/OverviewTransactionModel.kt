package com.guide.rest.model.controller

import com.guide.rest.model.entity.TransactionEntity
import java.util.*

class OverviewTransactionModel(
    val targetAccount: String,
    val value: Double,
    val description: String,
    val date: Date,
    val id: String
)

fun TransactionEntity.convertToOverviewTransactionModel() = OverviewTransactionModel(
    targetAccount = this.accountIdentifier,
    value = this.value,
    description = this.description,
    date = this.date,
    id = this.id.toString()
)