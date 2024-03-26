package ru.zakat.bankappbackend.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.zakat.bankappbackend.dao.auth.AuthenticationResponse
import ru.zakat.bankappbackend.dao.auth.LoginRequest
import ru.zakat.bankappbackend.dao.auth.RegisterRequest
import ru.zakat.bankappbackend.service.AuthService

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest,
    ): AuthenticationResponse {
        return authService.register(request)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): AuthenticationResponse {
        return authService.login(request)
    }
}