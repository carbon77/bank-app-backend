package ru.zakat.bankappbackend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.security.jwt")
class JwtConfig(
    var secretKey: String = "",
    var expiration: Long = 0
)
