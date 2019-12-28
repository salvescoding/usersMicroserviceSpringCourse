package com.salves.photoapp.api.users.service

import com.salves.photoapp.api.users.domain.UserDto
import com.salves.photoapp.api.users.domain.UserEntity
import com.salves.photoapp.api.users.domain.UsersRepository
import com.salves.photoapp.api.users.web.models.AlbumResponseModel
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


@Service
class UsersServiceImpl(
        private val usersRepository: UsersRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val restTemplate: RestTemplate,
        private val env : Environment) : UsersService {


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
        return usersRepository.getUserByUsername(email)?.toUserDto() ?: throw UsernameNotFoundException(email)
    }

    override fun getUserById(userId: String) : UserDto {
        val user = usersRepository.findByUserId(userId)?.toUserDto() ?: throw UsernameNotFoundException(userId)
        val params = mapOf("userId" to userId)
        val albumUrl = env.getProperty("albums.url") ?: ""
        if (albumUrl.isNotEmpty()) {
            val response = getAlbumsForUser(albumUrl, params)
            user.albumResponseModel = response.body
        }
        return user
    }

    private fun getAlbumsForUser(albumUrl: String, params: Map<String, String>): ResponseEntity<List<AlbumResponseModel>> {
        val albumsUrlBuilder = UriComponentsBuilder.fromUriString(albumUrl)
        val response = restTemplate.exchange(
                albumsUrlBuilder.buildAndExpand(params).toUri(),
                HttpMethod.GET, null, object : ParameterizedTypeReference<List<AlbumResponseModel>>() {})
        return response
    }

    private fun UserEntity.toUserDto() : UserDto = UserDto(firstName, lastName, email, userId, emptyList())
}
