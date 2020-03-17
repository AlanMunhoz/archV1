package com.example.archv1.data.repository

import com.example.archv1.data.network.AlbumService
import com.example.archv1.data.util.responseResult
import com.example.archv1.domain.repository.AlbumRepository

class AlbumRepositoryImpl(private val retrofitService: AlbumService) : AlbumRepository {

    override suspend fun getAlbum(albumId: Int) = retrofitService.getAlbum(albumId)

    override suspend fun getAlbumResponse(albumId: Int) =
        retrofitService.getAlbumResponse(albumId).responseResult()
}

