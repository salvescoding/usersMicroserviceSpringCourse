package com.salves.photoapp.api.users.domain

import com.salves.photoapp.api.users.web.models.AlbumResponseModel

data class UserDto(val firstName: String,
                   val lastName: String,
                   val email: String,
                   val userId: String,
                   var albumResponseModel: List<AlbumResponseModel>?)

