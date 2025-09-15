package com.todoapp.userservice.dto

import java.util.*

data class MessageResponse(
    val message: String
)

data class JwtResponse(
    val token: String,
    val type: String = "Bearer",
    val id: UUID? = null,
    val username: String,
)
