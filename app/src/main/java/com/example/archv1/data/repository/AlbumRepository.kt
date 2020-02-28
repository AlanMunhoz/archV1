package com.example.archv1.data.repository

import com.example.archv1.data.RetrofitInstance

class AlbumRepository {

    private var retrofitService = RetrofitInstance.RetrofitService

    suspend fun getAlbum(albumId: Int) = retrofitService.getAlbum(albumId)
    suspend fun getAlbumResponse(albumId: Int) = retrofitService.getAlbumResponse(albumId)
}