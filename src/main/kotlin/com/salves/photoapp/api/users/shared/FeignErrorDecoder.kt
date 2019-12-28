package com.salves.photoapp.api.users.shared

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception

class FeignErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception = when {
        response.status() == 404 && methodKey.contains("getAlbums") ->  ResponseStatusException(HttpStatus.valueOf(response.status()), "Users Album not found")
        else -> Exception(response.reason())
    }
}
