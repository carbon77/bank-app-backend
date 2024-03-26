package ru.zakat.bankappbackend.dao.auth

data class LoginRequest(
    val email: String,
    val password: String,
)
