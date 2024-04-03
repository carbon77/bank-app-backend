package ru.zakat.bankappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository
import ru.zakat.bankappbackend.model.Passport

interface PassportRepository : JpaRepository<Passport, Long> {
}