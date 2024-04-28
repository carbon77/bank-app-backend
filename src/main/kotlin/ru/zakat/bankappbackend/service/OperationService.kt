package ru.zakat.bankappbackend.service

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.dto.CreateOperationRequest
import ru.zakat.bankappbackend.dto.CreateTransferRequest
import ru.zakat.bankappbackend.model.operation.Operation
import ru.zakat.bankappbackend.model.operation.OperationField
import ru.zakat.bankappbackend.model.operation.OperationType
import ru.zakat.bankappbackend.repository.AccountRepository
import ru.zakat.bankappbackend.repository.CardRepository
import ru.zakat.bankappbackend.repository.OperationCategoryRepository
import ru.zakat.bankappbackend.repository.OperationRepository
import java.time.Instant
import java.util.*

@Service
class OperationService(
    private val accountService: AccountService,
    private val operationRepository: OperationRepository,
    private val categoryRepository: OperationCategoryRepository,
    private val userService: UserService, private val accountRepository: AccountRepository,
    private val cardRepository: CardRepository,
) {

    @Transactional
    fun createOperation(req: CreateOperationRequest) {
        val account = accountService.findAccountById(req.accountId)
        if (req.type == OperationType.EXPENSE && req.amount >= account.balance!!) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money on the account")
        }

        val category = categoryRepository.findByName(req.category).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")
        }

        val operation = Operation(
            type = req.type,
            amount = req.amount,
            extraFields = req.extraFields,
            account = account,
            category = category,
            createdAt = Date.from(Instant.now()),
        )
        operationRepository.save(operation)

        if (operation.type == OperationType.EXPENSE) {
            account.balance = account.balance?.minus(operation.amount!!)
        } else {
            account.balance = account.balance?.plus(operation.amount!!)
        }
        accountRepository.save(account)
    }

    @Transactional
    fun createTransfer(req: CreateTransferRequest) {
        val senderAccount = accountService.findAccountById(req.senderAccountId)
        val sender = senderAccount.user!!
        val recipient = userService.findUser(req.recipientCard)
        val recipientCard = cardRepository.findByNumber(req.recipientCard).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        }
        val extraFields: List<OperationField> = if (req.message == null) listOf() else listOf(
            OperationField(name = "Сообщение", value = req.message)
        )
        val accountFromRequest = CreateOperationRequest(
            type = OperationType.EXPENSE,
            amount = req.amount,
            extraFields = listOf(
                OperationField(
                    name = "Получатель",
                    value = "${recipient.passport!!.firstName!!} ${recipient.passport!!.lastName!![0]}.",
                ),
                OperationField(
                    name = "Карта получателя",
                    value = req.recipientCard,
                )
            ) + extraFields,
            accountId = req.senderAccountId,
            category = "Перевод",
        )
        val accountToRequest = CreateOperationRequest(
            type = OperationType.RECEIPT,
            amount = req.amount,
            extraFields = listOf(
                OperationField(
                    name = "Отправитель",
                    value = "${sender.passport!!.firstName!!} ${sender.passport!!.lastName!![0]}.",
                ),
            ) + extraFields,
            accountId = recipientCard.account!!.id!!,
            category = "Перевод",
        )

        createOperation(accountFromRequest)
        createOperation(accountToRequest)
    }

    @Transactional(readOnly = true)
    fun findOperationsByAccount(accountId: Long): List<Operation> {
        val account = accountService.findAccountById(accountId)
        return operationRepository.findByAccount(account)
    }

    @Transactional(readOnly = true)
    fun findOperationsByUser(auth: Authentication): List<Operation> {
        val user = userService.getAuthorizedUser(auth)
        return operationRepository.findByUser(user)
    }
}