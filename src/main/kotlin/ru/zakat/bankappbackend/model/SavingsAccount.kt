package ru.zakat.bankappbackend.model

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "savings_accounts")
class SavingsAccount : Account() {
    var rate: Double? = null
}