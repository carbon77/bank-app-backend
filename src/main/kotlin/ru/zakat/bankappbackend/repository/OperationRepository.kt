package ru.zakat.bankappbackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.zakat.bankappbackend.model.Account
import ru.zakat.bankappbackend.model.User
import ru.zakat.bankappbackend.model.operation.Operation

interface OperationRepository : JpaRepository<Operation, Long> {
    fun findByAccount(account: Account): List<Operation>

    @Query("select o from Operation o where o.account.user = ?1")
    fun findByUser(user: User): List<Operation>

    @Query(
        "SELECT op.category AS category, op.type AS type, SUM(op.amount) AS totalAmount FROM Operation op " +
                "WHERE op.status = 'SUCCESS' AND op.account.user = ?1 " +
                "GROUP BY op.category, op.type "
    )
    fun findOperationCategoriesByUser(user: User): List<Map<String, Any>>


    @Query(
        "SELECT op.category AS category, op.type AS type, SUM(op.amount) AS totalAmount FROM Operation op " +
                "WHERE op.status = 'SUCCESS' AND op.account = ?1 " +
                "GROUP BY op.category, op.type"
    )
    fun findOperationCategoriesByAccount(account: Account): List<Map<String, Any>>

    @Query(
        "SELECT date_trunc('month', op.createdAt) AS month, " +
                "op.type AS type, " +
                "SUM(op.amount) AS total " +
                "FROM Operation op " +
                "WHERE op.status = 'SUCCESS' AND op.account.user = ?1 " +
                "GROUP BY month, op.type"
    )
    fun findOperationTypesTotalGroupedByMonthsAndUser(user: User): List<Map<String?, Any>>

    @Query(
        "SELECT date_trunc('month', op.createdAt) AS month, " +
                "op.type AS type, " +
                "SUM(op.amount) AS total " +
                "FROM Operation op " +
                "WHERE op.status = 'SUCCESS' AND op.account = ?1 " +
                "GROUP BY month, op.type"
    )
    fun findOperationTypesTotalGroupedByMonthsAndAccount(account: Account): List<Map<String?, Any>>
}