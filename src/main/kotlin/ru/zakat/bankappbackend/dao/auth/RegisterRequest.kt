package ru.zakat.bankappbackend.dao.auth

import ru.zakat.bankappbackend.model.Passport

data class RegisterRequest(
    val email: String,
    val password: String,
    val phoneNumber: String,
    val passport: Passport,
)