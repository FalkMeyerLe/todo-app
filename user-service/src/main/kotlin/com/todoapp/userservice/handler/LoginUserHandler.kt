package com.todoapp.userservice.handler

import com.todoapp.userservice.dto.JwtResponse
import com.todoapp.userservice.security.JwtUtils
import com.todoapp.userservice.service.UserDetailsImpl
import jakarta.validation.constraints.NotBlank
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

data class LoginUserRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Password is required")
    val password: String
){
    @Component
    class Handler(
        private val authenticationManager: AuthenticationManager,
        private val jwtUtils: JwtUtils
    ) {

        fun handle(request: LoginUserRequest): JwtResponse {
            try {
                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        request.username,
                        request.password
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                val jwt = jwtUtils.generateJwtToken(request.username)

                val userDetails = authentication.principal as UserDetailsImpl

                return JwtResponse(
                    token = jwt,
                    id = userDetails.id,
                    username = userDetails.username,
                )
            } catch (e: Exception) {
                println("Authentication failed: ${e.message}")
                e.printStackTrace()
                throw IllegalArgumentException("Authentication failed: ${e.message}")
            }
        }
    }
}