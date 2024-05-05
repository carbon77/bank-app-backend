package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.operation.OperationType
import java.util.Date

interface OperationMonthDto {
    fun getMonth(): Date
    fun getType(): OperationType
    fun getTotal(): Double
}