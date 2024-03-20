package ru.zakat.bankappbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankAppBackendApplication

fun main(args: Array<String>) {
	runApplication<BankAppBackendApplication>(*args)
}
