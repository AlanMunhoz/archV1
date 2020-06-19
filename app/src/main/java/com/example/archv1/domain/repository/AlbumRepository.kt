package com.example.archv1.domain.repository

import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult

interface AlbumRepository {

    suspend fun getAlbum(albumId: Int): Album
    suspend fun getAlbumResponse(albumId: Int): ResponseResult<Album>
    suspend fun getAlbumListResponse(): ResponseResult<List<Album>>
}
