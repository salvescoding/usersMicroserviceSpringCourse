package com.salves.photoapp.api.users.infrastructure

import com.salves.photoapp.api.users.web.models.AlbumResponseModel
import feign.FeignException
import feign.hystrix.FallbackFactory
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallbackFactory::class)
interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albumss")
    fun getAlbums(@PathVariable id: String) : List<AlbumResponseModel>
}


@Component
class AlbumsFallbackFactory : FallbackFactory<AlbumsServiceClient> {
    override fun create(cause: Throwable): AlbumsServiceClient  = AlbumsServiceClientFallback(cause)
}

