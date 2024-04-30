package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.operation.OperationField
import ru.zakat.bankappbackend.model.operation.OperationStatus
import ru.zakat.bankappbackend.model.operation.OperationType

data class CreateOperationRequest(
    val type: OperationType,
    val amount: Double,
    val extraFields: List<OperationField>? = null,
    val accountId: Long,
    val category: String,
    val status: OperationStatus = OperationStatus.SUCCESS,
)