package com.todoapp.userservice.controller

import com.todoapp.userservice.dto.JwtResponse
import com.todoapp.userservice.dto.MessageResponse
import com.todoapp.userservice.handler.LoginUserRequest
import com.todoapp.userservice.handler.RegisterUserRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/user")
class UserController(
    private val loginUserHandler: LoginUserRequest.Handler,
    private val registerUserHandler: RegisterUserRequest.Handler,
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

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody request: LoginUserRequest): ResponseEntity<JwtResponse?> {
        return try {
            val jwtResponse = loginUserHandler.handle(request)
            ResponseEntity.ok(jwtResponse)
        } catch (e: Exception) {
            println("Login failed: ${e.message}")
            e.printStackTrace()
            ResponseEntity.badRequest()
                .body(null)
        }
    }
}