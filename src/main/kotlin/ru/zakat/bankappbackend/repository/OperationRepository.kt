package ru.zakat.bankappbackend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.zakat.bankappbackend.dto.CategoryGroupDto
import ru.zakat.bankappbackend.dto.OperationMonthDto
import ru.zakat.bankappbackend.model.User
import ru.zakat.bankappbackend.model.operation.Operation
import ru.zakat.bankappbackend.model.operation.OperationType
import java.util.*

interface OperationRepository : JpaRepository<Operation, Long> {
    @Query(
        "SELECT op FROM Operation op " +
                "WHERE ((?1 IS NULL AND op.account.user = ?2) OR op.account.id IN ?1) AND " +
                "cast(op.createdAt AS date) >= COALESCE(?3, cast('01-01-1900' as date)) AND " +
                "cast(op.createdAt AS date) <= COALESCE(?4, cast('01-01-2200' as date)) AND " +
                "(?5 IS NULL OR op.type = ?5) " +
                "ORDER BY op.createdAt DESC"
    )
    fun findOperations(
        accountIds: List<Long>?,
        user: User,
        pageable: Pageable,
        startDate: Date?,
        endDate: Date?,
        type: OperationType?,
    ): Page<Operation>


    @Query(
        "SELECT op.category AS category, op.type AS type, SUM(op.amount) AS totalAmount FROM Operation op " +
                "WHERE op.status = 'SUCCESS' AND " +
                "((?1 IS NULL AND op.account.user = ?2) OR op.account.id IN ?1) AND " +
                "cast(op.createdAt AS date) >= COALESCE(?3, cast('01-01-1900' as date)) AND " +
                "cast(op.createdAt AS date) <= COALESCE(?4, cast('01-01-2200' as date)) " +
                "GROUP BY op.category, op.type "
    )
    fun findCategoryGroups(
        accountIds: List<Long>?,
        user: User,
        startDate: Date?,
        endDate: Date?
    ): List<CategoryGroupDto>


    @Query(
        "SELECT date_trunc('month', op.createdAt) AS month, " +
                "op.type AS type, " +
                "SUM(op.amount) AS total " +
                "FROM Operation op " +
                "WHERE op.status = 'SUCCESS' AND" +
                " ((?1 IS NULL AND op.account.user = ?2) OR op.account.id IN ?1) AND " +
                "cast(op.createdAt AS date) >= COALESCE(?3, cast('01-01-1900' as date)) AND " +
                "cast(op.createdAt AS date) <= COALESCE(?4, cast('01-01-2200' as date)) " +
                "GROUP BY month, op.type"
    )
    fun findOperationsGroupedByTypeAndMonth(
        accountIds: List<Long>?, user: User,
        startDate: Date?,
        endDate: Date?
    ): List<OperationMonthDto>
}