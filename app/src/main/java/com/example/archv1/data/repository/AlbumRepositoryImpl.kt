package com.example.archv1.data.repository

import android.util.Log
import com.example.archv1.data.network.AlbumService
import com.example.archv1.data.preferences.SharedPrefs
import com.example.archv1.data.util.responseResult
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.domain.repository.AlbumRepository

class AlbumRepositoryImpl(private val retrofitService: AlbumService) : AlbumRepository {

    companion object {
        val LOG_TAG = AlbumRepositoryImpl::class.java.name.split(".").lastOrNull()?.toString()
    }

    override suspend fun getAlbum(albumId: Int) = retrofitService.getAlbum(albumId)

    override suspend fun getAlbumResponse(albumId: Int): ResponseResult<Album> {
        val prefs = SharedPrefs()
        prefs.getAlbum(albumId)?.let {
            Log.d(LOG_TAG, "Return from preferences (${it})")
            return ResponseResult.Success(it)
        } ?: run {
            val response = retrofitService.getAlbumResponse(albumId).responseResult()
            if (response is ResponseResult.Success) {
                prefs.saveAlbum(response.data)
                Log.d(LOG_TAG, "Return from api (${response.data})")
            }
            return response
        }
    }
}

