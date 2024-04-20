package ru.zakat.bankappbackend.dto.auth

data class LoginRequest(
    val email: String,
    val password: String,
)
