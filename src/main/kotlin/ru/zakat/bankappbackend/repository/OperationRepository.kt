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

}