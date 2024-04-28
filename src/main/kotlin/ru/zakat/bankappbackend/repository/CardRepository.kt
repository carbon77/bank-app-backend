package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.model.Card
import java.util.*

interface CardRepository : JpaRepository<Card, Long> {
    fun findByAccount(account: Account): Optional<Card>
    fun findByNumber(number: String): Optional<Card>
}