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
import ru.zakat.bankappbackend.utils.paymentInfoDtoList

@Service
class PaymentInfoService(
    private val paymentInfoRepository: PaymentInfoRepository,
    private val operationCategoryRepository: OperationCategoryRepository,
) {
    @Bean
    fun createInfos(): CommandLineRunner = CommandLineRunner {
        val paymentInfos = paymentInfoDtoList {
            paymentInfo {
                categoryName = "Мобильная связь"
                field {
                    name = "Телефон"
                    type = PaymentFieldType.STRING
                    pattern = "+# (###) ###-##-##"
                }

                field {
                    name = "Оператор"
                    type = PaymentFieldType.CHOICE
                    choices = listOf("МТС", "Билайн", "Мегафон", "Yota", "Теле2")
                }
            }

            paymentInfo {
                categoryName = "Интернет"
                field {
                    name = "Лицевой счёт"
                    type = PaymentFieldType.STRING
                }
                field {
                    name = "Тариф"
                    type = PaymentFieldType.CHOICE
                    choices = listOf(
                        "Эконом (400 руб./мес.)",
                        "Стандарт (800 руб./мес.)",
                        "Премиум (1500 руб./мес.)"
                    )
                }
            }

            paymentInfo {
                categoryName = "Газ"
                field {
                    name = "Лицевой счёт"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    maxLength = 12
                    minLength = 12
                }

                field {
                    name = "Объём газа"
                    type = PaymentFieldType.NUMBER
                    suffix = "куб.м."
                }
            }

            paymentInfo {
                categoryName = "Электроэнергия"
                field {
                    name = "Лицевой счёт"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    maxLength = 12
                    minLength = 12
                }

                field {
                    name = "Показания счётчиков"
                    type = PaymentFieldType.NUMBER
                    suffix = "кВт."
                }
            }

            paymentInfo {
                categoryName = "Водоснабжение"
                field {
                    name = "Лицевой счёт"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    maxLength = 12
                    minLength = 12
                }

                field {
                    name = "Показания счётчиков"
                    type = PaymentFieldType.NUMBER
                    suffix = "куб.м."
                }
            }

            paymentInfo {
                categoryName = "Транспортная карта"
                field {
                    name = "Номер карты"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    maxLength = 12
                    minLength = 12
                }
            }

            paymentInfo {
                categoryName = "Штрафы ГИБДД"
                field {
                    name = "Свидетельство ТС"
                    type = PaymentFieldType.STRING
                    pattern = "## ## №######"
                }

                field {
                    name = "Водительское удостоверение"
                    type = PaymentFieldType.STRING
                    pattern = "## ## ######"
                }

                field {
                    name = "Номер постановления"
                    type = PaymentFieldType.STRING
                    description = "Укажите номер постановления без пробелов. Пример: 18810148140204007407"
                    maxLength = 20
                    minLength = 20
                }
            }

            paymentInfo {
                categoryName = "По реквизитам"
                field {
                    name = "Номер счёта"
                    type = PaymentFieldType.STRING
                    description = "Счёт получателя"
                    maxLength = 12
                    minLength = 12
                }

                field {
                    name = "БИК"
                    type = PaymentFieldType.STRING
                    description = "БИК банка получателя"
                    maxLength = 9
                    minLength = 9
                }

                field {
                    name = "ИНН"
                    type = PaymentFieldType.STRING
                    description = "ИНН получателя"
                    maxLength = 10
                    minLength = 10
                }

                field {
                    name = "Назначение платежа"
                    type = PaymentFieldType.STRING
                }
            }
        }

        try {
            paymentInfos.forEach { (categoryName, fields) ->
                create(categoryName, fields)
            }
        } catch (e: ResponseStatusException) {
            println(e)
        } finally {
            println("Информация о платежах добавлена")
        }
    }

    @Transactional
    fun create(categoryName: String, fields: List<PaymentField>): PaymentInfo {
        val category = operationCategoryRepository.findByName(categoryName).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена : $categoryName")
        }

        if (paymentInfoRepository.existsByCategory(category)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment info already exists")
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