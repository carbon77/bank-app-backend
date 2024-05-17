package ru.zakat.bankappbackend.dto.auth

data class LoginRequest(
    var email: String,
    var password: String,
)
