package ru.zakat.bankappbackend.dao

data class PatchUserRequest(
    var email: String? = null,
    var phoneNumber: String? = null,
)
