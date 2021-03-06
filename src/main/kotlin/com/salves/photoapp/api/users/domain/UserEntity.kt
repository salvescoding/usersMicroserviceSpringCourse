package com.salves.photoapp.api.users.domain

import com.salves.photoapp.api.users.web.models.AlbumResponseModel
import java.io.Serializable

class UserEntity(
         val firstName: String,
         val lastName: String,
         val email: String,
         val userId: String,
         val encryptedPass: String
         ) : Serializable {
}
