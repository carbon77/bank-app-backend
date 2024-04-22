package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.operation.OperationField
import ru.zakat.bankappbackend.model.operation.OperationType

data class CreateOperationRequest(
    var type: OperationType,
    var amount: Double,
    var extraFields: List<OperationField>? = null,
    var accountId: Long,
    var category: String,
)