package ru.zakat.bankappbackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import ru.zakat.bankappbackend.dto.auth.LoginRequest
import ru.zakat.bankappbackend.dto.auth.RegisterRequest
import ru.zakat.bankappbackend.model.Passport
import ru.zakat.bankappbackend.service.AuthService
import java.time.Instant
import java.util.*


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
abstract class AbstractIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var authService: AuthService

    protected fun getLoginRequest() = LoginRequest(
        email = "zakatov@example.com",
        password = "password"
    )

    protected fun getRegisterRequest() = RegisterRequest(
        email = "test@example.com",
        password = "password",
        phoneNumber = "799999999",
        passport = Passport(
            firstName = "Ivan",
            lastName = "Ivanov",
            patronimic = "Ivanovich",
            series = "3412",
            number = "123412",
            issueDate = Date.from(Instant.now()),
            birthday = Date.from(Instant.now()),
            departmentCode = "124-124",
        ),
    )

    protected fun getAuthorizationHeader(): String = "Bearer " + authService.login(getLoginRequest()).jwtToken
}