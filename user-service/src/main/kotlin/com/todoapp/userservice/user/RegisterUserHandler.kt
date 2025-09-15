package com.todoapp.userservice.user

import com.todoapp.userservice.entity.User
import com.todoapp.userservice.repository.UserRepository
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.stereotype.Component

data class RegisterUserRequest(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    val password: String
)

@Component
class RegisterUserHandler(
    private val userRepository: UserRepository
) {
    fun handle(request: RegisterUserRequest) {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Error: Username is already taken!")
        }

        val user = User(
            username = request.username,
            password = request.password,
        )
        userRepository.save(user)
    }
}
