package com.todoapp.todoservice.controller

import com.todoapp.todoservice.dto.TodoRequest
import com.todoapp.todoservice.dto.TodoResponse
import com.todoapp.todoservice.handler.CreateTodoRequest
import com.todoapp.todoservice.handler.GetTodosByUsernameHandler
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody


@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val createTodoHandler: CreateTodoRequest.Handler,
    private val getTodosByUsernameHandler: GetTodosByUsernameHandler,
) {

    @GetMapping
    fun getAllTodos( authentication: Authentication): ResponseEntity<List<TodoResponse>> {
        val username = authentication.name
        return ResponseEntity.ok(getTodosByUsernameHandler.handle(username))
    }

    @PostMapping
    fun createTodo(
        @Valid @RequestBody todoRequest: TodoRequest,
        authentication: Authentication
    ): ResponseEntity<TodoResponse> {
        val username = authentication.name
        val createdTodo = createTodoHandler.handle(todoRequest, username)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo)
    }
}