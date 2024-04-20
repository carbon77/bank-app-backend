package ru.zakat.bankappbackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.zakat.bankappbackend.dto.PatchUserRequest
import ru.zakat.bankappbackend.model.User
import ru.zakat.bankappbackend.service.UserService

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
class UsersController(
    private val userService: UserService,
) {

    @GetMapping("/me")
    fun getAuthorizedUser(auth: Authentication): User {
        return userService.getAuthorizedUser(auth)
    }

    @PatchMapping
    fun patchUser(auth: Authentication, @RequestBody req: PatchUserRequest): ResponseEntity<Any> {
        userService.patchUser(auth, req)
        return ResponseEntity.noContent().build()
    }
}