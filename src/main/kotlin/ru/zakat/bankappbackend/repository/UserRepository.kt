package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.zakat.bankappbackend.model.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun existsByEmailIgnoreCase(email: String): Boolean
    fun existsByPhoneNumber(phoneNumber: String): Boolean

    @Query(
        value = "SELECT c.account.user FROM Card c WHERE c.number = ?1 AND c.account.closed = FALSE"
    )
    fun findByCardNumber(cardNumber: String): Optional<User>
}