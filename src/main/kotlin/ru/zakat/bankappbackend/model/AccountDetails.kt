package ru.zakat.bankappbackend.model

import jakarta.persistence.Embeddable

@Embeddable
data class AccountDetails(
    var number: String? = null,
    var bankName: String? = null,
    var bik: String? = null,
    var correctionAccount: String? = null,
    var inn: String? = null,
)
