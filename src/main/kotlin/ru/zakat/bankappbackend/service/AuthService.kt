package ru.zakat.bankappbackend.service

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.dao.auth.AuthenticationResponse
import ru.zakat.bankappbackend.dao.auth.LoginRequest
import ru.zakat.bankappbackend.dao.auth.RegisterRequest
import ru.zakat.bankappbackend.model.User
import ru.zakat.bankappbackend.repository.PassportRepository

@Service
class AuthService(
    private val passportRepository: PassportRepository,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {

    fun register(request: RegisterRequest) {
        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            phoneNumber = request.phoneNumber,
        )

        try {
            userService.save(user)
        } catch (e: DataIntegrityViolationException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }

        request.passport.user = user
        passportRepository.save(request.passport)
    }

    fun login(request: LoginRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email, request.password
            )
        )
        val user = userService.loadUserByUsername(request.email)
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken)
    }
}