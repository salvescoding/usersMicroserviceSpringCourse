package com.salves.photoapp.api.users.infrastructure

import com.salves.photoapp.api.users.web.models.AlbumResponseModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "albums-ws")
interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    fun getAlbums(@PathVariable id: String) : List<AlbumResponseModel>
}


