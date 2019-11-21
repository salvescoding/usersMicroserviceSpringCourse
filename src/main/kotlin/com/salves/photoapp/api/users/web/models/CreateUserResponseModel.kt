package com.salves.photoapp.api.users.web.models

data class CreateUserResponseModel(
          val firstName: String,
          val lastName: String,
          val email: String,
          val userId: String) {
}
