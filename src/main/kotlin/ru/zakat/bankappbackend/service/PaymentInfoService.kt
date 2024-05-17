package ru.zakat.bankappbackend.service

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.model.AccountDetails
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
                details {
                    number = "14184109292489437547"
                    bankName = "Сбербанк"
                    bik = "4719204124"
                    inn = "123938401200"
                    correctionAccount = "38105849037495617035"
                }
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
                details {
                    number = "98657413476573498765"
                    bankName = "Газпромбанк"
                    bik = "5839208476"
                    inn = "567832109754"
                    correctionAccount = "27461029385012345678"
                }
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
                details {
                    number = "65439821764328756789"
                    bankName = "Альфа-Банк"
                    bik = "7391482034"
                    inn = "876543210987"
                    correctionAccount = "12345678901234567890"
                }
                field {
                    name = "Лицевой счёт"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    pattern = "#### #### #### ####"
                }

                field {
                    name = "Объём газа"
                    type = PaymentFieldType.NUMBER
                    suffix = "куб.м."
                }
            }

            paymentInfo {
                categoryName = "Электроэнергия"
                details {
                    number = "45769832476548392811"
                    bankName = "Тинькофф Банк"
                    bik = "2938475698"
                    inn = "654321098765"
                    correctionAccount = "09876543210987654321"
                }
                field {
                    name = "Лицевой счёт"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    pattern = "#### #### #### ####"
                }

                field {
                    name = "Показания счётчиков"
                    type = PaymentFieldType.NUMBER
                    suffix = "кВт."
                }
            }

            paymentInfo {
                categoryName = "Водоснабжение"
                details {
                    number = "27384910283746587493"
                    bankName = "ВТБ"
                    bik = "1827364509"
                    inn = "987654321098"
                    correctionAccount = "56789012345678901234"
                }
                field {
                    name = "Лицевой счёт"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    pattern = "#### #### #### ####"
                }

                field {
                    name = "Показания счётчиков"
                    type = PaymentFieldType.NUMBER
                    suffix = "куб.м."
                }
            }

            paymentInfo {
                categoryName = "Транспортная карта"
                details {
                    number = "98476215348021563830"
                    bankName = "Росбанк"
                    bik = "5891023476"
                    inn = "876543210987"
                    correctionAccount = "01234567893456789012"
                }
                field {
                    name = "Номер карты"
                    type = PaymentFieldType.STRING
                    description = "Введите 12 цифр"
                    pattern = "#### #### #### ####"
                }
            }

            paymentInfo {
                categoryName = "Штрафы ГИБДД"
                details {
                    number = "12345678901234567890"
                    bankName = "Райффайзенбанк"
                    bik = "9083476512"
                    inn = "123456789012"
                    correctionAccount = "76543210987654321098"
                }
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
                    pattern = "####################"
                }
            }

            paymentInfo {
                categoryName = "По реквизитам"
                field {
                    name = "Номер счёта"
                    type = PaymentFieldType.STRING
                    description = "Счёт получателя"
                    pattern = "####################"
                }

                field {
                    name = "БИК"
                    type = PaymentFieldType.STRING
                    description = "БИК банка получателя"
                    pattern = "#########"
                }

                field {
                    name = "ИНН"
                    type = PaymentFieldType.STRING
                    description = "ИНН получателя"
                    pattern = "##########"
                }

                field {
                    name = "Назначение платежа"
                    type = PaymentFieldType.STRING
                }
            }
        }

        try {
            paymentInfos.forEach { (categoryName, details, fields) ->
                create(categoryName, details, fields)
            }
        } catch (e: ResponseStatusException) {
            println(e)
        } finally {
            println("Информация о платежах добавлена")
        }
    }

    @Transactional
    fun create(categoryName: String, details: AccountDetails, fields: List<PaymentField>): PaymentInfo {
        val category = operationCategoryRepository.findByName(categoryName).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена : $categoryName")
        }

        if (paymentInfoRepository.existsByCategory(category)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment info already exists")
        }

        val paymentInfo = PaymentInfo(
            category = category,
            fields = fields,
            accountDetails = details
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