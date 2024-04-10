package ru.zakat.bankappbackend.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
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
}