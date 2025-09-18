package com.todoapp.todoservice.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class JwtUtils {

    @Value("\${jwt.validation-url}")
    private lateinit var tokenValidationUrl: String

    fun validateJwtToken(jwt: String): Boolean {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $jwt")
        val requestEntity = HttpEntity<Void>(headers)
        val restTemplate = RestTemplate()
         try {
            val response: ResponseEntity<String> = restTemplate.exchange(
                tokenValidationUrl,
                HttpMethod.POST,
                requestEntity,
                String::class.java
            )
           val  bo = response.statusCode.is2xxSuccessful
        return    response.statusCode.is2xxSuccessful
        } catch (e: Exception) {
        return    false
        }
    }
}