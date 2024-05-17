package ru.zakat.bankappbackend

import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get

class UserIT : AbstractIT() {

    @Test
    @Sql("/data.sql")
    fun getAuthorizedUser_returnsOk() {
        mockMvc.get("/api/users/me") {
            headers {
                add("Authorization", getAuthorizationHeader())
            }
        }
            .andDo { print() }
            .andExpectAll {
                status { isOk() }
            }
    }

    @Test
    @Sql("/data.sql")
    fun getAuthorizedUserWithoutToken_returnsForbidden() {
        mockMvc.get("/api/users/me")
            .andDo { print() }
            .andExpectAll {
                status { isForbidden() }
            }
    }

    @Test
    @Sql("/data.sql")
    fun getAuthorizedUserWithInvalidToken_returnsForbidden() {
        mockMvc.get("/api/users/me") {
            headers {
                add("Authorization", "1238")
            }
        }
            .andDo { print() }
            .andExpectAll {
                status { isForbidden() }
            }
    }
}