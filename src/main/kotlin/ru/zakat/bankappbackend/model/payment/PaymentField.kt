package ru.zakat.bankappbackend.model.payment

data class PaymentField(
    val name: String,
    val description: String? = null,
    val type: PaymentFieldType,
    val pattern: String? = null,
    val maxLength: Int? = null,
    val minLength: Int = 0,
    val maxValue: Double? = null,
    val minValue: Double = 0.0,
    val suffix: String? = null,
    val choices: List<String>? = null,
)
