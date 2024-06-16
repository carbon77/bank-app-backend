package ru.zakat.bankappbackend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.dto.CategoryGroupDto
import ru.zakat.bankappbackend.dto.CreateOperationRequest
import ru.zakat.bankappbackend.dto.CreateTransferRequest
import ru.zakat.bankappbackend.dto.OperationMonthDto
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.model.operation.Operation
import ru.zakat.bankappbackend.model.operation.OperationField
import ru.zakat.bankappbackend.model.operation.OperationStatus
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
    private val accountRepository: AccountRepository,
    private val cardRepository: CardRepository,
) {

    @Transactional(noRollbackFor = [ResponseStatusException::class])
    fun createOperation(req: CreateOperationRequest) {
        val account = accountService.findAccountById(req.accountId)

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
            status = req.status,
        )

        if (req.type == OperationType.EXPENSE && req.amount > account.balance!!) {
            operation.status = OperationStatus.FAILED
            val fields = (if (operation.extraFields != null) operation.extraFields!! else listOf()) + listOf(
                OperationField(name = "Причина", value = "Недостаточно средств на счету")
            )
            operation.extraFields = fields
        }
        operationRepository.save(operation)

        if (operation.status === OperationStatus.FAILED) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Операция не произошла")
        }
        updateBalance(operation, account)
    }

    private fun updateBalance(
        operation: Operation,
        account: Account
    ) {
        if (operation.status == OperationStatus.FAILED) {
            return
        }

        if (operation.type == OperationType.EXPENSE) {
            account.balance = account.balance?.minus(operation.amount!!)
        } else {
            account.balance = account.balance?.plus(operation.amount!!)
        }
        accountRepository.save(account)
    }

    @Transactional(noRollbackFor = [ResponseStatusException::class])
    fun createTransfer(req: CreateTransferRequest) {
        val senderAccount = accountService.findAccountById(req.senderAccountId)
        val recipientCard = cardRepository.findByNumber(req.recipientCard).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        }
        val extraFields: List<OperationField> = if (req.message == null) listOf() else listOf(
            OperationField(name = "Сообщение", value = req.message)
        )
        val senderRequest = CreateOperationRequest(
            type = OperationType.EXPENSE,
            amount = req.amount,
            extraFields = extraFields + listOf(
                OperationField(
                    name = "Карта получателя",
                    value = req.recipientCard,
                )
            ),
            accountId = req.senderAccountId,
            category = "Перевод",
            status = if (recipientCard.blocked) OperationStatus.FAILED else OperationStatus.SUCCESS,
        )
        val operationFailed = req.amount > senderAccount.balance!! || recipientCard.blocked
        createOperation(senderRequest)

        if (operationFailed) {
            return
        }
        val recipientRequest = CreateOperationRequest(
            type = OperationType.RECEIPT,
            amount = req.amount,
            extraFields = extraFields + listOf(
                OperationField(
                    name = "Отправитель",
                    value = "${senderAccount.userId}",
                ),
            ),
            accountId = recipientCard.account!!.id!!,
            category = "Перевод",
        )
        createOperation(recipientRequest)
    }

    @Transactional(readOnly = true)
    fun getOperationCategories(
        auth: Authentication,
        accountIds: List<Long>?,
        startDate: Date?,
        endDate: Date?,
    ): List<CategoryGroupDto> {
        return operationRepository.findCategoryGroups(
            accountIds,
            auth.name,
            startDate,
            endDate,
        )
    }

    @Transactional(readOnly = true)
    fun findOperationStatsByMonths(
        auth: Authentication, accountIds: List<Long>?,
        startDate: Date?,
        endDate: Date?
    ): List<OperationMonthDto> {
        return operationRepository.findOperationsGroupedByTypeAndMonth(
            accountIds, auth.name,
            startDate,
            endDate,
        )
    }

    @Transactional(readOnly = true)
    fun findOperations(
        auth: Authentication,
        accountIds: List<Long>?,
        pageable: Pageable,
        startDate: Date?,
        endDate: Date?,
        operationType: OperationType?,
    ): Page<Operation> {
        return operationRepository.findOperations(
            accountIds,
            auth.name,
            pageable,
            startDate,
            endDate,
            operationType
        )
    }
}