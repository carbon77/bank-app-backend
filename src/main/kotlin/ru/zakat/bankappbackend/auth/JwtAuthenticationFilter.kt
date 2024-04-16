package ru.zakat.bankappbackend.auth

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.zakat.bankappbackend.service.JwtService
import ru.zakat.bankappbackend.service.UserService

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userService: UserService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath.contains("/api/auth")) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader: String? = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt: String = authHeader.substring(7)

        val userEmail: String? = try {
            jwtService.extractUsername(jwt)
        } catch (e: ExpiredJwtException) {
            response.sendError(HttpServletResponse.SC_GONE, "Token expired!")
            return
        }

        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val user = userService.loadUserByUsername(userEmail)

            if (jwtService.isTokenValid(jwt, user)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.authorities
                )

                authToken.details = WebAuthenticationDetailsSource()
                    .buildDetails(request)

                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}