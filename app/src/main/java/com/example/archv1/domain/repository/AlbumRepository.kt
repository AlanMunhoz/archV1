package com.example.archv1.domain.repository

import com.example.archv1.data.model.Album
import retrofit2.Response

interface AlbumRepository {

    suspend fun getAlbum(albumId: Int) : Album
    suspend fun getAlbumResponse(albumId: Int) : Response<Album>
}