package com.salves.photoapp.api.users.web.models

import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class CreateUserRequestModel(
        @Size(min=3, max = 16, message = "Minimum of 3 chars") val firstName: String,
        @Size(min=3, max = 16, message = "Minimum of 3 chars") val lastName: String,
        @Email val email: String,
        @Size(min= 8, message = "Minimum of 8 characters") val password: String) {
}
