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
        val payments: List<PaymentInfo> = listOf(
            create(
                "Мобильная связь",
                listOf(
                    PaymentField(
                        name = "Телефон",
                        type = PaymentFieldType.STRING,
                        pattern = "+# (###) ###-##-##",
                    ),
                ),
            ),
            create(
                "Интернет",
                listOf(
                    PaymentField(
                        name = "Лицевой счёт",
                        type = PaymentFieldType.STRING,
                    ),
                    PaymentField(
                        name = "Тариф",
                        type = PaymentFieldType.CHOICE,
                        choices = listOf(
                            "Эконом (400 руб./мес.)",
                            "Стандарт (800 руб./мес.)",
                            "Премиум (1500 руб./мес.)"
                        )
                    ),
                )
            ),
            create(
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
            ),
            create(
                "Электроэнергия", listOf(
                    PaymentField(
                        name = "Лицевой счёт",
                        type = PaymentFieldType.STRING,
                        description = "Введите 12 цифр",
                        maxLength = 12,
                        minLength = 12,
                    ),
                    PaymentField(
                        name = "Показания счётчиков",
                        type = PaymentFieldType.NUMBER,
                        suffix = "кВт.",
                    )
                )
            ),
            create(
                "Водоснабжение", listOf(
                    PaymentField(
                        name = "Лицевой счёт",
                        type = PaymentFieldType.STRING,
                        description = "Введите 12 цифр",
                        maxLength = 12,
                        minLength = 12,
                    ),
                    PaymentField(
                        name = "Показания счётчиков",
                        type = PaymentFieldType.NUMBER,
                        suffix = "куб.м.",
                    )
                )
            ),
            create(
                "Транспортная карта", listOf(
                    PaymentField(
                        name = "Номер карты",
                        type = PaymentFieldType.STRING,
                        description = "Введите 12 цифр",
                        maxLength = 12,
                        minLength = 12,
                    ),
                )
            ),
            create(
                "Штрафы ГИБДД", listOf(
                    PaymentField(
                        name = "Свидетельство ТС",
                        type = PaymentFieldType.STRING,
                        pattern = "## ## №######",
                    ),
                    PaymentField(
                        name = "Водительское удостоверение",
                        type = PaymentFieldType.STRING,
                        pattern = "## ## ######",
                    ),
                    PaymentField(
                        name = "Номер постановления",
                        type = PaymentFieldType.STRING,
                        description = "Укажите номер постановления без пробелов. Пример: 18810148140204007407",
                        maxLength = 20,
                        minLength = 20,
                    ),
                )
            ),
            create(
                "По реквизитам", listOf(
                    PaymentField(
                        name = "Номер счёта",
                        type = PaymentFieldType.STRING,
                        description = "Счёт получателя",
                        maxLength = 12,
                        minLength = 12,
                    ),
                    PaymentField(
                        name = "БИК",
                        type = PaymentFieldType.STRING,
                        description = "БИК банка получателя",
                        maxLength = 9,
                        minLength = 9,
                    ),
                    PaymentField(
                        name = "ИНН",
                        type = PaymentFieldType.STRING,
                        description = "ИНН получателя",
                        maxLength = 10,
                        minLength = 10,
                    ),
                    PaymentField(
                        name = "Назначение платежа",
                        type = PaymentFieldType.STRING,
                    )
                )
            ),
        )

        println("Созданы описания платежей:")
        payments.forEach { println(it) }
    }

    @Transactional
    fun create(categoryName: String, fields: List<PaymentField>): PaymentInfo {
        val category = operationCategoryRepository.findByName(categoryName).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена : $categoryName")
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