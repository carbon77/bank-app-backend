package ru.zakat.bankappbackend.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.zakat.bankappbackend.dao.CreateAccountRequest
import ru.zakat.bankappbackend.dao.CreateAccountResponse
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.service.AccountService
import ru.zakat.bankappbackend.service.CardService

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")
class AccountController(
    private val accountService: AccountService,
    private val cardService: CardService,
) {

    @GetMapping("/mine")
    fun findMyAccount(auth: Authentication): List<Account> {
        return accountService.getAuthorizedUserAccounts(auth)
    }

    @PostMapping
    fun create(auth: Authentication, @RequestBody req: CreateAccountRequest): CreateAccountResponse {
        return accountService.create(auth, req)
    }

    @PostMapping("/{accountId}/card")
    fun addCard(auth: Authentication, @PathVariable accountId: Long): Long? {
        return cardService.createRandomCard(accountId)
    }

    @DeleteMapping("/{accountId}/card/{cardId}")
    fun deleteCard(auth: Authentication, @PathVariable accountId: Long, @PathVariable cardId: Long) {
        cardService.deleteCard(accountId, cardId)
    }
}