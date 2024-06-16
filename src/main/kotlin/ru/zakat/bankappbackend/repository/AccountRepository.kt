package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.Account

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUserId(userId: String): List<Account>
}