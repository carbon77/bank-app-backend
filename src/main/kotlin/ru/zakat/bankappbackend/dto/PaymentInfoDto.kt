package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.payment.PaymentField

data class PaymentInfoDto(
    var categoryName: String,
    var fields: List<PaymentField>,
)
