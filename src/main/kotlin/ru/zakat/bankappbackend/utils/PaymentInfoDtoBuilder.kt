package ru.zakat.bankappbackend.utils

import ru.zakat.bankappbackend.dto.PaymentInfoDto
import ru.zakat.bankappbackend.model.AccountDetails
import ru.zakat.bankappbackend.model.payment.PaymentField


class PaymentInfoDtoBuilder {
    var id: Int = 0
    var categoryName: String = ""
    var minAmount: Double = 0.0
    private var fields: MutableList<PaymentField> = mutableListOf()
    private var accountDetails: AccountDetails = AccountDetails()

    fun build(): PaymentInfoDto {
        return PaymentInfoDto(
            categoryName = categoryName,
            fields = fields,
            details = accountDetails,
            minAmount = minAmount,
        )
    }

    fun field(block: PaymentFieldBuilder.() -> Unit) {
        fields.add(PaymentFieldBuilder().apply(block).build())
    }

    fun details(block: AccountDetailsBuilder.() -> Unit) {
        accountDetails = AccountDetailsBuilder().apply(block).build()
    }
}

