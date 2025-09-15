package com.todoapp.userservice.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtUtils {
    
    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration}")
    private var jwtExpirationMs: Int = 0

    private val key: Key by lazy {
        Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    fun generateJwtToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getUsernameFromJwtToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: SecurityException) {
            println("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            println("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            println("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            println("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            println("JWT claims string is empty: ${e.message}")
        }
        return false
    }
}