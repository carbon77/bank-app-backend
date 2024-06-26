package ru.zakat.bankappbackend.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.dto.PatchCardRequest
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.model.Card
import ru.zakat.bankappbackend.repository.AccountRepository
import ru.zakat.bankappbackend.repository.CardRepository
import ru.zakat.bankappbackend.utils.generateRandomString
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Service
class CardService(
    private val cardRepository: CardRepository,
    private val accountRepository: AccountRepository,
) {

    @Transactional
    fun createRandomCard(accountId: Long): Long? {
        val account = accountRepository.findById(accountId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
        }

        val card = Card(
            account = account,
            number = generateRandomString(16),
            svv = generateRandomString(3).toInt(),
            expirationDate = Date.from(LocalDate.now().plusYears(3).atStartOfDay(ZoneId.systemDefault()).toInstant())
        )
        cardRepository.save(card)
        return card.id
    }

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

    @Transactional
    fun deleteCard(accountId: Long, cardId: Long) {
        val card = findCard(accountId, cardId)
        cardRepository.delete(card)
    }

    @Transactional
    fun patchCard(accountId: Long, cardId: Long, req: PatchCardRequest) {
        val card = findCard(accountId, cardId)
        card.blocked = req.blocked

        cardRepository.save(card)
    }

    @Transactional
    fun getUserFullNameByCard(cardNumber: String): Map<String, String> {
        val card = cardRepository.findByNumber(cardNumber).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        }
        return mapOf(
            "firstName" to card.account!!.userFirstName!!,
            "lastName" to card.account!!.userLastName!!,
        )
    }

    private fun findCard(accountId: Long, cardId: Long): Card {
        val account = accountRepository.findById(accountId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
        }
        val card = cardRepository.findById(cardId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        }

        if (card.account != account) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Account doesn't have this card")
        }

        return card
    }
}