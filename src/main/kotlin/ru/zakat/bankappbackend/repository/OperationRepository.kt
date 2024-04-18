package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.operation.Operation

interface OperationRepository : JpaRepository<Operation, Long> {
}