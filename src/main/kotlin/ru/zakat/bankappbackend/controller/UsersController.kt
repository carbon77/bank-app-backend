package ru.zakat.bankappbackend.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import ru.zakat.bankappbackend.dto.PatchUserRequest
import ru.zakat.bankappbackend.model.User
import ru.zakat.bankappbackend.service.UserService

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Пользователи")
@CrossOrigin("*")
class UsersController(
    private val userService: UserService,
) {

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    fun getAuthorizedUser(auth: Authentication): User {
        return userService.getAuthorizedUser(auth)
    }

    @Operation(summary = "Редактирование пользователя")
    @PatchMapping
    fun patchUser(auth: Authentication, @RequestBody req: PatchUserRequest): ResponseEntity<Any> {
        userService.patchUser(auth, req)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Получение имени пользователя по номеру карты")
    @GetMapping("/findByCard")
    fun findUser(
        @RequestParam cardNumber: String,
    ): User {
        return userService.findUser(cardNumber)
    }
}