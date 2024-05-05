package ru.zakat.bankappbackend.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.zakat.bankappbackend.model.payment.PaymentInfo
import ru.zakat.bankappbackend.service.PaymentInfoService

@RestController
@RequestMapping("/api/payments")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Платежи")
@CrossOrigin("*")
class PaymentController(
    private val paymentInfoService: PaymentInfoService,
) {

    @GetMapping("/info/{categoryName}")
    fun findByCategoryName(@PathVariable categoryName: String): PaymentInfo {
        return paymentInfoService.findByCategory(categoryName)
    }
}