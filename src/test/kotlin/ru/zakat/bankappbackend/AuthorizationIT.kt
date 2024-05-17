package ru.zakat.bankappbackend

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.post

class AuthorizationIT : AbstractIT() {
    @Test
    @Sql("/data.sql")
    fun register_returnsMessageSuccess() {
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(getRegisterRequest())
        }
            .andDo { print() }
            .andExpectAll {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    content {
                        json(
                            """
                                {
                                    "message": "Success"
                                }
                            """.trimIndent()
                        )
                    }
                }
            }
    }

    @Test
    @Sql("/data.sql")
    fun registerWithExistingEmail_returnsBadRequest() {
        val registerRequest = getRegisterRequest()
        registerRequest.email = "zakatov@example.com"
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(registerRequest)
        }
            .andDo { print() }
            .andExpectAll {
                status {
                    isBadRequest()
                    reason("Email already exists")
                }
            }
    }

    @Test
    @Sql("/data.sql")
    fun registerWithExistingPhoneNumber_returnsBadRequest() {
        val registerRequest = getRegisterRequest()
        registerRequest.phoneNumber = "79160016224"
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(registerRequest)
        }
            .andDo { print() }
            .andExpectAll {
                status {
                    isBadRequest()
                    reason("Phone number already exists")
                }
            }
    }

    @Test
    @Sql("/data.sql")
    fun registerWithExistingPassport_returnsBadRequest() {
        val registerRequest = getRegisterRequest()
        registerRequest.passport.series = "4123"
        registerRequest.passport.number = "123456"
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(registerRequest)
        }
            .andDo { print() }
            .andExpectAll {
                status {
                    isBadRequest()
                    reason("Passport already exists")
                }
            }
    }

    @Test
    @Sql("/data.sql")
    fun login_returnsOk() {
        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(getLoginRequest())
        }
            .andDo { print() }
            .andExpectAll {
                status { isOk() }
            }
    }

    @Test
    @Sql("/data.sql")
    fun loginWithWrongCredentials_returnsForbidden() {
        val loginRequest = getLoginRequest()
        loginRequest.password = "12984"
        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(loginRequest)
        }
            .andDo { print() }
            .andExpectAll {
                status { isForbidden() }
            }
    }
}
