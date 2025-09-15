package com.todoapp.todoservice.entity


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "todos")
data class Todo(
    @Id
    @GeneratedValue
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    val title: String,

    @Column(length = 500)
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    val description: String? = null,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)