package ru.zakat.bankappbackend.dao

import com.fasterxml.jackson.databind.JsonNode
import ru.zakat.bankappbackend.model.operation.OperationType

data class CreateOperationRequest(
    var type: OperationType,
    var amount: Double,
    var extraFields: JsonNode? = null,
    var accountId: Long,
    var categoryId: Long,
)