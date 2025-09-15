package com.todoapp.todoservice.repository

import com.todoapp.todoservice.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoRepository : JpaRepository<Todo, UUID> {
    fun findByUsername(username: String): List<Todo>
}