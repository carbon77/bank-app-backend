package ru.zakat.bankappbackend.dto

import ru.zakat.bankappbackend.model.operation.OperationCategory
import ru.zakat.bankappbackend.model.operation.OperationType

interface CategoryGroupDto {
    fun getCategory(): OperationCategory
    fun getType(): OperationType
    fun getTotalAmount(): Double
}
