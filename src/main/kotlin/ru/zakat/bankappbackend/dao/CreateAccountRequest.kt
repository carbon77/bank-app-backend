package ru.zakat.bankappbackend.dao

import ru.zakat.bankappbackend.model.AccountType

data class CreateAccountRequest(
    var accountType: AccountType,
    var extraFields: Map<String, Any>? = null
)