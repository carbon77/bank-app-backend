package ru.zakat.bankappbackend.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.zakat.bankappbackend.dao.CreateAccountRequest
import ru.zakat.bankappbackend.dao.CreateAccountResponse
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.service.AccountService

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")
class AccountController(
    private val accountService: AccountService,
) {

    @GetMapping("/mine")
    fun findMyAccount(auth: Authentication): List<Account> {
        return accountService.getAuthorizedUserAccounts(auth)
    }

    @PostMapping
    fun create(auth: Authentication, @RequestBody req: CreateAccountRequest): CreateAccountResponse {
        return accountService.create(auth, req)
    }
}