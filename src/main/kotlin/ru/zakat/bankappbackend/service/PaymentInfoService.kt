package ru.zakat.bankappbackend.service

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.model.payment.PaymentField
import ru.zakat.bankappbackend.model.payment.PaymentFieldType
import ru.zakat.bankappbackend.model.payment.PaymentInfo
import ru.zakat.bankappbackend.repository.OperationCategoryRepository
import ru.zakat.bankappbackend.repository.PaymentInfoRepository

@Service
class PaymentInfoService(
    private val paymentInfoRepository: PaymentInfoRepository,
    private val operationCategoryRepository: OperationCategoryRepository,
) {
    @Bean
    fun createInfos(): CommandLineRunner = CommandLineRunner {
        var payment = create(
            "Мобильная связь", listOf(
                PaymentField(
                    name = "Телефон",
                    type = PaymentFieldType.STRING,
                    pattern = "+# (###) ###-##-##",
                ),
            )
        )
        println("Создан платеж: $payment")

        payment = create(
            "Газ", listOf(
                PaymentField(
                    name = "Лицевой счёт",
                    type = PaymentFieldType.STRING,
                    description = "Введите 12 цифр",
                    maxLength = 12,
                    minLength = 12,
                ),
                PaymentField(
                    name = "Объём газа",
                    type = PaymentFieldType.NUMBER,
                    suffix = "куб.м.",
                )
            )
        )
        println("Создан платеж: $payment")
    }

    @Transactional
    fun create(categoryName: String, fields: List<PaymentField>): PaymentInfo {
        val category = operationCategoryRepository.findByName(categoryName).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена")
        }
        val paymentInfo = PaymentInfo(
            category = category,
            fields = fields,
        )
        paymentInfoRepository.save(paymentInfo)
        return paymentInfo
    }

    @Transactional(readOnly = true)
    fun findByCategory(categoryName: String): PaymentInfo {
        val category = operationCategoryRepository.findByName(categoryName).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена")
        }

        return paymentInfoRepository.findByCategory(category).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Информация о платеже не найдена")
        }
    }
}