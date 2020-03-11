package com.example.archv1.data.repository

import com.example.archv1.data.AlbumService

class AlbumRepository(private val retrofitService: AlbumService) {

    suspend fun getAlbum(albumId: Int) = retrofitService.getAlbum(albumId)
    suspend fun getAlbumResponse(albumId: Int) = retrofitService.getAlbumResponse(albumId)
}