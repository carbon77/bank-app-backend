package ru.zakat.bankappbackend.dto

data class ChangePasswordRequest(
    val password: String,
    val newPassword: String,
)
