package com.salves.photoapp.api.users.security

import com.salves.photoapp.api.users.service.UsersService
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurity(private val environment: Environment,
                  private val usersService: UsersService,
                  private val bCryptPasswordEncoder: BCryptPasswordEncoder
                  ) : WebSecurityConfigurerAdapter() {


    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/**")
                .hasIpAddress(environment.getProperty("gateway.ip"))
                .and()
                .addFilter(AuthenticationFilter(usersService, environment, authenticationManager()))
        http.headers().frameOptions().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(usersService)?.passwordEncoder(bCryptPasswordEncoder)
    }
}
