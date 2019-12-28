package com.salves.photoapp.api.users

import com.salves.photoapp.api.users.shared.FeignErrorDecoder
import feign.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class PhotoapiusersApplication {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @LoadBalanced
    fun getRestTemplate() : RestTemplate = RestTemplate()

    @Bean
    fun feignLogger() : Logger.Level = Logger.Level.FULL

    @Bean
    fun getFeignErrorDecoder() : FeignErrorDecoder = FeignErrorDecoder()
}

fun main(args: Array<String>) {
    runApplication<PhotoapiusersApplication>(*args)
}

