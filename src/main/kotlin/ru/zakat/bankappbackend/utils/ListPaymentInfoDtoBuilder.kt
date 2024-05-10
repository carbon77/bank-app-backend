package ru.zakat.bankappbackend.utils

import ru.zakat.bankappbackend.dto.PaymentInfoDto

fun paymentInfoDtoList(block: ListPaymentInfoDtoBuilder.() -> Unit): List<PaymentInfoDto> {
    return ListPaymentInfoDtoBuilder().apply(block).build()
}

class ListPaymentInfoDtoBuilder {
    var dtos: MutableList<PaymentInfoDto> = mutableListOf()

    fun build(): List<PaymentInfoDto> {
        return dtos
    }

    fun paymentInfo(block: PaymentInfoDtoBuilder.() -> Unit) {
        dtos.add(PaymentInfoDtoBuilder().apply(block).build())
    }
}