package ru.zakat.bankappbackend.dto.auth

import ru.zakat.bankappbackend.model.Passport

data class RegisterRequest(
    var email: String,
    var password: String,
    var phoneNumber: String,
    var passport: Passport,
)