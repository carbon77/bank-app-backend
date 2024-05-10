package ru.zakat.bankappbackend.utils

import ru.zakat.bankappbackend.dto.PaymentInfoDto
import ru.zakat.bankappbackend.model.payment.PaymentField


class PaymentInfoDtoBuilder {
    var id: Int = 0
    var categoryName: String = ""
    var fields: MutableList<PaymentField> = mutableListOf()

    fun build(): PaymentInfoDto {
        return PaymentInfoDto(categoryName = categoryName, fields = fields)
    }

    fun field(block: PaymentFieldBuilder.() -> Unit) {
        fields.add(PaymentFieldBuilder().apply(block).build())
    }
}

