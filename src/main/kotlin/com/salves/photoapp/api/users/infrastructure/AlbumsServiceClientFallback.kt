package com.salves.photoapp.api.users.infrastructure

import com.salves.photoapp.api.users.web.models.AlbumResponseModel
import feign.FeignException
import org.slf4j.LoggerFactory

class AlbumsServiceClientFallback(private val throwable: Throwable) : AlbumsServiceClient {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun getAlbums(id: String): List<AlbumResponseModel> {
        logErrorMessage(throwable, id)
        return emptyList()
    }

    private fun logErrorMessage(throwable: Throwable, id: String) {
        if (throwable is FeignException && throwable.status() == 404) {
            logger.error("404 error took place when " +
                    "get albums was called with userId: $id, Error message: ${throwable.localizedMessage}")
        } else {
            logger.error("Other error took place, Error message: ${throwable.localizedMessage}")
        }
    }
}
