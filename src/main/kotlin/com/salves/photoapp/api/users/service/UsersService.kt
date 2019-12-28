package com.salves.photoapp.api.users.service

import com.salves.photoapp.api.users.domain.UserDto
import com.salves.photoapp.api.users.domain.UserEntity
import org.springframework.security.core.userdetails.UserDetailsService

interface UsersService : UserDetailsService {
    fun createUser(userEntity: UserEntity)
    fun getUserDetailsByEmail(email: String) : UserDto
    fun getUserById(userId: String): UserDto
}
