package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.AccountDetails
import ru.zakat.bankappbackend.model.payment.PaymentField

data class PaymentInfoDto(
    var categoryName: String,
    var minAmount: Double,
    var details: AccountDetails,
    var fields: List<PaymentField>,
)
