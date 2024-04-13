package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.model.User

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUser(user: User): List<Account>
}