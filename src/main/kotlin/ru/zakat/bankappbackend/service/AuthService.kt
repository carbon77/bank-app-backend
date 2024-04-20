package ru.zakat.bankappbackend.service

import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.zakat.bankappbackend.dto.auth.AuthenticationResponse
import ru.zakat.bankappbackend.dto.auth.LoginRequest
import ru.zakat.bankappbackend.dto.auth.RegisterRequest
import ru.zakat.bankappbackend.model.User
import ru.zakat.bankappbackend.repository.PassportRepository
import ru.zakat.bankappbackend.repository.UserRepository

@Service
class AuthService(
    private val passportRepository: PassportRepository,
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {

    fun register(request: RegisterRequest) {
        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            phoneNumber = request.phoneNumber,
        )

        if (userRepository.existsByEmailIgnoreCase(request.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists")
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists")
        }

        if (passportRepository.existsBySeriesAndNumber(request.passport.series, request.passport.number)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Passport already exists")
        }

        try {
            userRepository.save(user)
        } catch (e: DuplicateKeyException) {
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