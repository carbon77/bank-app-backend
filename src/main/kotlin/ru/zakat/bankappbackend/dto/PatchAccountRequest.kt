package ru.zakat.bankappbackend.dto

data class PatchAccountRequest(
    val name: String? = null,
    val closed: Boolean? = null,
)
