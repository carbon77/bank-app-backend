package ru.zakat.bankappbackend.service

import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.dao.CreateOperationRequest
import ru.zakat.bankappbackend.dao.CreateTransferRequest
import ru.zakat.bankappbackend.model.operation.Operation
import ru.zakat.bankappbackend.model.operation.OperationCategory
import ru.zakat.bankappbackend.model.operation.OperationType
import ru.zakat.bankappbackend.repository.OperationCategoryRepository
import ru.zakat.bankappbackend.repository.OperationRepository
import java.time.Instant
import java.util.*

@Service
class OperationService(
    private val accountService: AccountService,
    private val operationRepository: OperationRepository,
    private val categoryRepository: OperationCategoryRepository,
    private val userService: UserService,
) {

    @Transactional
    fun createOperation(req: CreateOperationRequest) {
        if (req.type == OperationType.EXPENSE) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money on the account")
        }

        val account = accountService.findAccountById(req.accountId)
        val category = categoryRepository.findById(req.categoryId).orElseThrow {
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
    }

    @Transactional
    fun createTransfer(req: CreateTransferRequest) {
        val transferCategory = categoryRepository.findByName("Перевод").orElseGet {
            val newCategory = OperationCategory(name = "Перевод")
            categoryRepository.save(newCategory)
            newCategory
        }
        val accountFromRequest = CreateOperationRequest(
            type = OperationType.EXPENSE,
            amount = req.amount,
            extraFields = req.extraFieldsFrom,
            accountId = req.accountIdFrom,
            categoryId = transferCategory.id!!,
        )
        val accountToRequest = CreateOperationRequest(
            type = OperationType.RECEIPT,
            amount = req.amount,
            extraFields = req.extraFieldsTo,
            accountId = req.accountIdTo,
            categoryId = transferCategory.id!!,
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