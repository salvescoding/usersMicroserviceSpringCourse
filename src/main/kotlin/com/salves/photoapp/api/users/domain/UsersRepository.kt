package com.salves.photoapp.api.users.domain

import org.springframework.stereotype.Repository

@Repository
class UsersRepository {
    private val userRepo  = mutableMapOf<String, UserEntity>()

    fun save(userEntity: UserEntity) { userRepo[userEntity.email] = userEntity }

    fun getUserByUsername(username: String) = userRepo[username]
}
