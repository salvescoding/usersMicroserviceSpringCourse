package com.salves.photoapp.api.users.service

import com.salves.photoapp.api.users.domain.UserDto
import com.salves.photoapp.api.users.domain.UsersRepository
import com.salves.photoapp.api.users.domain.UserEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsersServiceImpl(
        private val usersRepository: UsersRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
                       ) : UsersService {


    override fun loadUserByUsername(username: String): UserDetails {
        val user = usersRepository.getUserByUsername(username)
        return if (user != null) User(user.email, user.encryptedPass, true, true, true, true, ArrayList())
        else throw UsernameNotFoundException(username)
    }

    override fun createUser(userEntity: UserEntity)  {
        val encryptUser = UserEntity(
                userId = userEntity.userId,
                firstName = userEntity.firstName,
                lastName = userEntity.lastName,
                encryptedPass = bCryptPasswordEncoder.encode(userEntity.encryptedPass),
                email = userEntity.email
        )
        usersRepository.save(encryptUser)
    }

    override fun getUserDetailsByEmail(email: String) : UserDto {
        val user = usersRepository.getUserByUsername(email) ?: throw UsernameNotFoundException(email)
        return UserDto(user.firstName, user.lastName, user.email, user.userId)
    }
}
