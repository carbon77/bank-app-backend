package ru.zakat.bankappbackend.dto

import com.fasterxml.jackson.databind.JsonNode

data class CreateTransferRequest(
    var accountIdFrom: Long,
    var accountIdTo: Long,
    var amount: Double,
    var extraFieldsFrom: JsonNode? = null,
    var extraFieldsTo: JsonNode? = null,
)