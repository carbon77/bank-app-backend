package ru.zakat.bankappbackend.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.zakat.bankappbackend.dao.PatchUserRequest
import ru.zakat.bankappbackend.model.User
import ru.zakat.bankappbackend.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username).orElseThrow {
            UsernameNotFoundException("User not found")
        }
    }

    fun getAuthorizedUser(auth: Authentication): User {
        return loadUserByUsername(auth.name) as User
    }

    @Transactional
    fun patchUser(auth: Authentication, req: PatchUserRequest) {
        val user = getAuthorizedUser(auth)

        user.email = req.email ?: user.email
        user.phoneNumber = req.phoneNumber ?: user.phoneNumber

        userRepository.save(user)
    }
}