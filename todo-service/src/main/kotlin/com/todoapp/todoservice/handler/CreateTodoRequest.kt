package com.todoapp.todoservice.handler

import com.todoapp.todoservice.dto.TodoRequest
import com.todoapp.todoservice.dto.TodoResponse
import com.todoapp.todoservice.entity.Todo
import com.todoapp.todoservice.mapper.toResponse
import com.todoapp.todoservice.repository.TodoRepository
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.stereotype.Component

data class CreateTodoRequest(
    @field:NotBlank(message = "Title is required")
    @field:Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    val title: String,

    @field:Size(max = 500, message = "Description cannot exceed 500 characters")
    val description: String? = null
) {
    @Component
    class Handler(private val todoRepository: TodoRepository) {

        fun handle(todoRequest: TodoRequest, username: String): TodoResponse {
            val todo = Todo(
                title = todoRequest.title,
                description = todoRequest.description,
                username = username
            )
            val savedTodo = todoRepository.save(todo)
            return savedTodo.toResponse()
        }

    }
}