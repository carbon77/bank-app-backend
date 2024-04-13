package ru.zakat.bankappbackend.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.model.Card
import ru.zakat.bankappbackend.repository.CardRepository
import ru.zakat.bankappbackend.utils.generateRandomString
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
class CardService(
    private val cardRepository: CardRepository,
) {

    @Transactional
    fun createRandomCard(account: Account): Card {
        val card = Card(
            account = account,
            number = generateRandomString(16),
            svv = generateRandomString(3).toInt(),
            expirationDate = Date.from(LocalDate.now().plusYears(3).atStartOfDay(ZoneId.systemDefault()).toInstant())
        )
        cardRepository.save(card)
        return card
    }
}