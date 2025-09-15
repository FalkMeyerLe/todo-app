package com.todoapp.todoservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class TodoRequest(
    @field:NotBlank(message = "Title is required")
    @field:Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    val title: String,

    @field:Size(max = 500, message = "Description cannot exceed 500 characters")
    val description: String? = null
)


data class TodoResponse(
    val id: UUID,
    val title: String,
    val description: String?,
    val username: String,
    val createdAt: LocalDateTime
)

data class MessageResponse(
    val message: String
)