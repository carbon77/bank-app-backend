package ru.zakat.bankappbackend.service

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.dto.CreateAccountRequest
import ru.zakat.bankappbackend.dto.CreateAccountResponse
import ru.zakat.bankappbackend.model.*
import ru.zakat.bankappbackend.repository.AccountRepository
import ru.zakat.bankappbackend.utils.generateRandomString

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val userService: UserService,
    private val cardService: CardService,
) {

    @Transactional(readOnly = true)
    fun findAccountById(accountId: Long): Account {
        return accountRepository.findById(accountId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found")
        }
    }

    @Transactional(readOnly = true)
    fun getAuthorizedUserAccounts(auth: Authentication): List<Account> {
        return accountRepository.findByUser(userService.getAuthorizedUser(auth))
    }

    @Transactional
    fun create(auth: Authentication, req: CreateAccountRequest): CreateAccountResponse {
        val account = generateAccountByType(req.accountType)
        account.accountType = req.accountType
        account.accountDetails = generateAccountDetails()
        account.user = userService.getAuthorizedUser(auth)
        account.balance = 0.0
        account.name = req.name
        setExtraFields(account, req)

        accountRepository.save(account)
        cardService.createRandomCard(account)
        return CreateAccountResponse(accountId = account.id)
    }

    private fun setExtraFields(account: Account, req: CreateAccountRequest) {
        if (account is SavingsAccount) {
            account.rate = req.extraFields?.get("rate").toString().toDouble()
        } else if (account is CreditAccount) {
            account.accountLimit = req.extraFields?.get("accountLimit").toString().toDouble()
            account.interestRate = req.extraFields?.get("interestRate").toString().toDouble()
            account.balance = req.extraFields?.get("accountLimit").toString().toDouble()
        }
    }

    private fun generateAccountByType(type: AccountType): Account = when (type) {
        AccountType.CHECKING -> {
            Account()
        }

        AccountType.CREDIT -> {
            CreditAccount()
        }

        else -> {
            SavingsAccount()
        }
    }

    private fun generateAccountDetails(): AccountDetails {
        val details = AccountDetails()
        details.number = generateRandomString(20)
        details.bankName = "BankWave"
        details.bik = generateRandomString(9)
        details.inn = generateRandomString(10)
        details.correctionAccount = generateRandomString(20)
        return details
    }
}