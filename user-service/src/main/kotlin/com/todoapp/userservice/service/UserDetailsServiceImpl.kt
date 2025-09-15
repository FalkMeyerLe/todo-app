package com.todoapp.userservice.service

import com.todoapp.userservice.entity.User
import com.todoapp.userservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User Not Found: $username") } // Vulnerability: This could be used to guess usernames
        return UserDetailsImpl.build(user)
    }
}

class UserDetailsImpl(
    val id: UUID,
    private val username: String,
    private val password: String
) : UserDetails {

    companion object {
        fun build(user: User): UserDetailsImpl {
            return UserDetailsImpl(
                user.id,
                user.username,
                user.password
            )
        }
    }

    override fun getAuthorities() = mutableListOf<org.springframework.security.core.GrantedAuthority>()
    override fun getPassword() = password
    override fun getUsername() = username
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}