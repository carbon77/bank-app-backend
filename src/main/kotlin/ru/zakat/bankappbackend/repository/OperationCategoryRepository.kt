package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.operation.OperationCategory

interface OperationCategoryRepository : JpaRepository<OperationCategory, Long> {
}