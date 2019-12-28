package com.salves.photoapp.api.users.web.models

data class AlbumResponseModel(
        val albumId: String,
        val userId: String,
        val name: String,
        val description: String
) {

}
