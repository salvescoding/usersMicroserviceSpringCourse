package com.salves.photoapp.api.users.web.controllers

import com.salves.photoapp.api.users.service.UsersService
import com.salves.photoapp.api.users.web.models.CreateUserRequestModel
import com.salves.photoapp.api.users.web.models.CreateUserResponseModel
import com.salves.photoapp.api.users.web.models.convertToUser
import com.salves.photoapp.api.users.web.models.convertToUserResponse
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UsersController(private val env: Environment, private val userService : UsersService) {

    @GetMapping("/status")
    fun status() = "Working on port: ${env.getProperty("local.server.port")}"

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
                 produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@Valid @RequestBody createUserRequestModel: CreateUserRequestModel): ResponseEntity<CreateUserResponseModel> {
        val newUser = createUserRequestModel.convertToUser()
        userService.createUser(newUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser.convertToUserResponse())
    }


}
