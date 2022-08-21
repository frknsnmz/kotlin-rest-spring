package com.guide.rest.controller

import com.guide.rest.model.controller.OverviewTransactionModel
import com.guide.rest.model.controller.TransactionModel
import com.guide.rest.model.controller.convertToDBModel
import com.guide.rest.model.controller.convertToOverviewTransactionModel
import com.guide.rest.repository.TransferRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transfer")
class TransferController(val repository: TransferRepository) {

    @PostMapping("/new")
    fun newTransfer(@RequestBody transactionModel: TransactionModel) {
        repository.save(transactionModel.convertToDBModel())
    }

    @GetMapping("/all")
    fun getAllTransfers(): List<OverviewTransactionModel> {
        return repository.findAll().map { it.convertToOverviewTransactionModel() }
    }
}