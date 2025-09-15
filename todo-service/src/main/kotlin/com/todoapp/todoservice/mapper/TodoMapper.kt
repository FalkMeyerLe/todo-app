package com.todoapp.todoservice.mapper

import com.todoapp.todoservice.dto.TodoResponse
import com.todoapp.todoservice.entity.Todo

fun Todo.toResponse() = TodoResponse(
    id = this.id,
    title = this.title,
    description = this.description,
    username = this.username,
    createdAt = this.createdAt
)