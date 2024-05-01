package ru.zakat.bankappbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.zakat.bankappbackend.dto.CreateOperationRequest
import ru.zakat.bankappbackend.dto.CreateTransferRequest
import ru.zakat.bankappbackend.model.operation.Operation
import ru.zakat.bankappbackend.service.OperationService

@RestController
@RequestMapping("/api/operations")
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
    fun findByUserOrAccount(auth: Authentication, @RequestParam accountId: Long?): List<Operation> {
        if (accountId != null) {
            return operationService.findOperationsByAccount(accountId)
        }
        return operationService.findOperationsByUser(auth)
    }

    @GetMapping("/categories")
    fun findOperationCategories(auth: Authentication, @RequestParam accountId: Long?): List<Map<String, Any>> {
        return operationService.getOperationCategories(auth, accountId)
    }

    @GetMapping("/stats/months")
    fun findOperationStatsByMonths(auth: Authentication, @RequestParam accountId: Long?): List<Map<String?, Any>> {
        return operationService.findOperationStatsByMonths(auth, accountId)
    }
}