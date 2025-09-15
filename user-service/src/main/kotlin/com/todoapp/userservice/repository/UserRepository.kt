package com.todoapp.userservice.repository

import com.todoapp.userservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): Optional<User>
    fun existsByUsername(username: String): Boolean
}