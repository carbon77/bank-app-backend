package ru.zakat.bankappbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties
class BankAppBackendApplication

fun main(args: Array<String>) {
	runApplication<BankAppBackendApplication>(*args)
}
