package com.todoapp.userservice.controller

import com.todoapp.userservice.dto.MessageResponse
import com.todoapp.userservice.user.RegisterUserHandler
import com.todoapp.userservice.user.RegisterUserRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class UserController(
    private val registerUserHandler: RegisterUserHandler,
) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody registerRequest: RegisterUserRequest): ResponseEntity<MessageResponse> {
        return try {
            registerUserHandler.handle(registerRequest)
            ResponseEntity.ok(MessageResponse("User registered successfully!"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(MessageResponse(e.message ?: "Registration failed"))
        }
    }
}