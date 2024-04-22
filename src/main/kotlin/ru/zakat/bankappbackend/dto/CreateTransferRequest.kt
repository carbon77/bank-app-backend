package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.operation.OperationField

data class CreateTransferRequest(
    var accountIdFrom: Long,
    var accountIdTo: Long,
    var amount: Double,
    var extraFieldsFrom: List<OperationField>? = null,
    var extraFieldsTo: List<OperationField>? = null,
)