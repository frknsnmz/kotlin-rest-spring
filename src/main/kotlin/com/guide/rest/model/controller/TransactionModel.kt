package com.guide.rest.model.controller

import com.guide.rest.model.entity.TransactionEntity

class TransactionModel(
    val targetAccount: String,
    val value: Double,
    val description: String = "",
)

fun TransactionModel.convertToDBModel() = TransactionEntity(
    accountIdentifier = this.targetAccount,
    value = this.value,
    description = this.description
)