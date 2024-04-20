package ru.zakat.bankappbackend.dto

data class PatchUserRequest(
    var email: String? = null,
    var phoneNumber: String? = null,
)
