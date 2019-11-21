package com.salves.photoapp.api.users.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.salves.photoapp.api.users.domain.LoginRequestModel
import com.salves.photoapp.api.users.service.UsersService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList

class AuthenticationFilter(private val usersService: UsersService,
                           private val env : Environment,
                           authenticationManager_: AuthenticationManager
                           ) : UsernamePasswordAuthenticationFilter() {

    init {
        super.setAuthenticationManager(authenticationManager_)
    }

    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse?): Authentication {

            try {
                val credentials : LoginRequestModel = jacksonObjectMapper()
                        .readValue(request.inputStream, LoginRequestModel::class.java)

                return authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(
                                credentials.username,
                                credentials.password,
                                ArrayList()
                        )
                )
            } catch (ex : IOException) { throw IOException(ex.message ) }
    }


    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
                                          chain: FilterChain?, authResult: Authentication) {
        val username : String = (authResult.principal as User).username
        val userDetails = usersService.getUserDetailsByEmail(username)

        val token = Jwts.builder()
                .setSubject(userDetails.userId)
                .setExpiration(Date(System.currentTimeMillis() + env.getProperty("token.expiration_time")!!.toLong()))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact()
        response.addHeader("token", token)
        response.addHeader("userId", userDetails.userId)
    }
}
