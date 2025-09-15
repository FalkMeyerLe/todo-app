package com.todoapp.userservice.controller

import com.todoapp.userservice.dto.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class TokenController {
    @PostMapping("/token")
    fun validateToken(@RequestHeader("Authorization") authHeader: String): ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(MessageResponse("Token is valid"))
    }
}