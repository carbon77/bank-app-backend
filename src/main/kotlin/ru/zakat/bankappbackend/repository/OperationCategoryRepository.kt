package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.operation.OperationCategory
import java.util.Optional

interface OperationCategoryRepository : JpaRepository<OperationCategory, Long> {
    fun findByName(name: String): Optional<OperationCategory>
}