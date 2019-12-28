package com.salves.photoapp.api.users.domain

import org.springframework.stereotype.Repository

@Repository
class UsersRepository {
    private val userRepoByEmail  = mutableMapOf<String, UserEntity>()
    private val userRepoById  = mutableMapOf<String, UserEntity>()

    fun save(userEntity: UserEntity) {
        userRepoByEmail[userEntity.email] = userEntity
        userRepoById[userEntity.userId] = userEntity
    }

    fun getUserByUsername(username: String) = userRepoByEmail[username]

    fun findByUserId(userId: String) = userRepoById[userId]
}
