package ru.zakat.bankappbackend.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.zakat.bankappbackend.dto.CreateAccountRequest
import ru.zakat.bankappbackend.dto.CreateAccountResponse
import ru.zakat.bankappbackend.dto.PatchAccountRequest
import ru.zakat.bankappbackend.dto.PatchCardRequest
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

    @PatchMapping("/{accountId}")
    fun patchAccount(auth: Authentication, @PathVariable accountId: Long, @RequestBody req: PatchAccountRequest) {
        accountService.patchAccount(auth, accountId, req)
    }

    @PostMapping("/{accountId}/card")
    fun addCard(auth: Authentication, @PathVariable accountId: Long): Long? {
        return cardService.createRandomCard(accountId)
    }


    @DeleteMapping("/{accountId}/card/{cardId}")
    fun deleteCard(auth: Authentication, @PathVariable accountId: Long, @PathVariable cardId: Long) {
        cardService.deleteCard(accountId, cardId)
    }

    @PatchMapping("/{accountId}/card/{cardId}")
    fun patchCard(
        auth: Authentication,
        @PathVariable accountId: Long,
        @PathVariable cardId: Long,
        @RequestBody req: PatchCardRequest
    ) {
        cardService.patchCard(accountId, cardId, req)
    }
}