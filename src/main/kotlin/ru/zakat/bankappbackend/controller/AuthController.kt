package ru.zakat.bankappbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.zakat.bankappbackend.dto.auth.AuthenticationResponse
import ru.zakat.bankappbackend.dto.auth.LoginRequest
import ru.zakat.bankappbackend.dto.auth.RegisterRequest
import ru.zakat.bankappbackend.service.AuthService

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Авторизация")
@CrossOrigin("*")
class AuthController(
    private val authService: AuthService,
) {
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest,
    ): ResponseEntity<Map<String, String>> {
        authService.register(request)
        return ResponseEntity.ok(mapOf("message" to "Success"))
    }

    @Operation(summary = "Вход в систему")
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): AuthenticationResponse {
        return authService.login(request)
    }
}