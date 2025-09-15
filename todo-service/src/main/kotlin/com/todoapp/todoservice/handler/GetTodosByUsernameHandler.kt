package com.todoapp.todoservice.handler

import com.todoapp.todoservice.dto.TodoResponse
import com.todoapp.todoservice.mapper.toResponse
import com.todoapp.todoservice.repository.TodoRepository
import org.springframework.stereotype.Component

@Component
class GetTodosByUsernameHandler(
    private val todoRepository: TodoRepository
) {

    fun handle(username: String): List<TodoResponse> {
        return todoRepository.findByUsername(username).map { it.toResponse() }
    }
}