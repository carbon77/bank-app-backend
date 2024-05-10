package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.operation.OperationCategory
import ru.zakat.bankappbackend.model.payment.PaymentInfo
import java.util.Optional

interface PaymentInfoRepository : JpaRepository<PaymentInfo, Long> {

    fun findByCategory(category: OperationCategory): Optional<PaymentInfo>
    fun existsByCategory(category: OperationCategory): Boolean
}