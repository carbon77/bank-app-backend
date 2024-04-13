package ru.zakat.bankappbackend.model

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "credit_accounts")
class CreditAccount : Account() {
    var accountLimit: Double? = null
    var interestRate: Double? = null
}