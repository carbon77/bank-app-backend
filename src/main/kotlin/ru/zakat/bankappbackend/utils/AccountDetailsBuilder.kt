package ru.zakat.bankappbackend.utils

import ru.zakat.bankappbackend.model.AccountDetails

class AccountDetailsBuilder {
    var number: String? = null
    var bankName: String? = null
    var bik: String? = null
    var correctionAccount: String? = null
    var inn: String? = null

    fun build(): AccountDetails = AccountDetails(
        number = number,
        bankName = bankName,
        bik = bik,
        correctionAccount = correctionAccount,
        inn = inn,
    )
}