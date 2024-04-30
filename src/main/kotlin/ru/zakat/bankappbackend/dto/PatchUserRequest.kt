package ru.zakat.bankappbackend.dto

data class PatchUserRequest(
    val email: String? = null,
    val phoneNumber: String? = null,
)
