package com.salves.photoapp.api.users.web.models

import com.salves.photoapp.api.users.domain.UserEntity
import java.util.*

fun CreateUserRequestModel.convertToUser() : UserEntity {
    return UserEntity(
            userId = UUID.randomUUID().toString(),
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            encryptedPass = this.password
    )
}

fun UserEntity.convertToUserResponse() : CreateUserResponseModel {
    return CreateUserResponseModel(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            userId = this.userId
    )
}

