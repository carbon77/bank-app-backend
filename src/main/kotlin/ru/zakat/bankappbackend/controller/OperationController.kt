package ru.zakat.bankappbackend.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.zakat.bankappbackend.dto.CategoryGroupDto
import ru.zakat.bankappbackend.dto.CreateOperationRequest
import ru.zakat.bankappbackend.dto.CreateTransferRequest
import ru.zakat.bankappbackend.dto.OperationMonthDto
import ru.zakat.bankappbackend.model.operation.Operation
import ru.zakat.bankappbackend.model.operation.OperationType
import ru.zakat.bankappbackend.service.OperationService
import java.util.*

@RestController
@RequestMapping("/api/operations")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Операции")
@CrossOrigin("*")
class OperationController(
    private val operationService: OperationService,
) {

    @PostMapping
    fun create(@RequestBody req: CreateOperationRequest): ResponseEntity<Any> {
        operationService.createOperation(req)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/transfer")
    fun createTransfer(@RequestBody req: CreateTransferRequest): ResponseEntity<Any> {
        operationService.createTransfer(req)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun findOperations(
        auth: Authentication,
        pageable: Pageable,
        @RequestParam accountIds: List<Long>?,
        @RequestParam startDate: Date?,
        @RequestParam endDate: Date?,
        @RequestParam type: OperationType?,
    ): Page<Operation> {
        return operationService.findOperations(auth, accountIds, pageable, startDate, endDate, type);
    }

    @GetMapping("/stats/categories")
    fun findOperationCategories(
        auth: Authentication,
        @RequestParam accountIds: List<Long>?,
        @RequestParam startDate: Date?,
        @RequestParam endDate: Date?,
    ): List<CategoryGroupDto> {
        return operationService.getOperationCategories(auth, accountIds, startDate, endDate)
    }

    @GetMapping("/stats/months")
    fun findOperationStatsByMonths(
        auth: Authentication,
        @RequestParam accountIds: List<Long>?,
        @RequestParam startDate: Date?,
        @RequestParam endDate: Date?,
    ): List<OperationMonthDto> {
        return operationService.findOperationStatsByMonths(auth, accountIds, startDate, endDate)
    }
}