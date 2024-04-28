package ru.zakat.bankappbackend.dto

data class CreateTransferRequest(
    val senderAccountId: Long,
    val recipientCard: String,
    val amount: Double,
    val message: String? = null,
)