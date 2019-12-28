package com.salves.photoapp.api.users.web.models

data class UserResponseModel(
        val userId: String,
        val firstName: String,
        val lastName: String,
        val  email: String,
        val albums : List<AlbumResponseModel>
) {


}
