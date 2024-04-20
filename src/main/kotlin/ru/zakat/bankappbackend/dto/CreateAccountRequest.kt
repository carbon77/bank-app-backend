package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.AccountType

data class CreateAccountRequest(
    var accountType: AccountType,
    var name: String,
    var extraFields: Map<String, Any>? = null
)