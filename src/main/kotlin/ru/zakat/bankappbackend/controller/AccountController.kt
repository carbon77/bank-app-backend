package ru.zakat.bankappbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.zakat.bankappbackend.dto.CreateAccountRequest
import ru.zakat.bankappbackend.dto.CreateAccountResponse
import ru.zakat.bankappbackend.dto.PatchAccountRequest
import ru.zakat.bankappbackend.dto.PatchCardRequest
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.service.AccountService
import ru.zakat.bankappbackend.service.CardService
import java.security.Principal

@RestController
@RequestMapping("/api/account")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Счета и карты")
@CrossOrigin("*")
class AccountController(
    private val accountService: AccountService,
    private val cardService: CardService,
) {

    @Operation(summary = "Получение счетов, авторизованного пользователя")
    @GetMapping("/mine")
    fun findMyAccount(auth: Authentication): List<Account> {
        return accountService.getAuthorizedUserAccounts(auth)
    }

    @Operation(summary = "Созданий счёта")
    @PostMapping
    fun create(auth: Authentication, @RequestBody req: CreateAccountRequest): CreateAccountResponse {
        return accountService.create(auth, req)
    }

    @Operation(summary = "Редактирование счёта")
    @PatchMapping("/{accountId}")
    fun patchAccount(auth: Authentication, @PathVariable accountId: Long, @RequestBody req: PatchAccountRequest) {
        accountService.patchAccount(auth, accountId, req)
    }

    @Operation(summary = "Добавление новой карты к счёту")
    @PostMapping("/{accountId}/card")
    fun addCard(auth: Authentication, @PathVariable accountId: Long): Long? {
        return cardService.createRandomCard(accountId)
    }

    @Operation(summary = "Удаление карты")
    @DeleteMapping("/{accountId}/card/{cardId}")
    fun deleteCard(auth: Authentication, @PathVariable accountId: Long, @PathVariable cardId: Long) {
        cardService.deleteCard(accountId, cardId)
    }

    @Operation(summary = "Получение имени пользователя по карте")
    @GetMapping("/findByCard")
    fun getUserFullNameByCard(principal: Principal, @RequestParam cardNumber: String): Map<String, String> {
        return cardService.getUserFullNameByCard(cardNumber)
    }

    @Operation(summary = "Редактирование карты")
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