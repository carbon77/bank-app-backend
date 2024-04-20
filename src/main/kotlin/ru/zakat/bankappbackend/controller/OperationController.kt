package ru.zakat.bankappbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.zakat.bankappbackend.dao.CreateOperationRequest
import ru.zakat.bankappbackend.dao.CreateTransferRequest
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
}