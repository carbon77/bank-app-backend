package ru.zakat.bankappbackend.utils

import ru.zakat.bankappbackend.model.payment.PaymentField
import ru.zakat.bankappbackend.model.payment.PaymentFieldType

class PaymentFieldBuilder {
    var name: String = ""
    var type: PaymentFieldType = PaymentFieldType.STRING
    var description: String? = null
    var pattern: String? = null
    var maxLength: Int? = null
    var minLength: Int = 0
    var choices: List<String>? = null
    var minValue: Double = 0.0
    var maxValue: Double? = null
    var suffix: String? = null

    fun build(): PaymentField {
        return PaymentField(
            name = name,
            description = description,
            type = type,
            pattern = pattern,
            maxLength = maxLength,
            minLength = minLength,
            choices = choices,
            minValue = minValue,
            maxValue = maxValue,
            suffix = suffix,
        )
    }
}