package com.example.archv1.data.repository

import android.util.Log
import com.example.archv1.data.network.AlbumService
import com.example.archv1.data.preferences.AlbumPrefs
import com.example.archv1.data.room.AlbumDao
import com.example.archv1.data.util.responseResult
import com.example.archv1.data.util.toAlbum
import com.example.archv1.data.util.toAlbumEntity
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.domain.repository.AlbumRepository

class AlbumRepositoryImpl(
    private val retrofitService: AlbumService,
    private val albumPrefs: AlbumPrefs,
    private val albumDao: AlbumDao
) : AlbumRepository {

    companion object {
        val LOG_TAG = AlbumRepositoryImpl::class.java.name.split(".").lastOrNull()?.toString()
    }

    override suspend fun getAlbum(albumId: Int) = retrofitService.getAlbum(albumId)

    override suspend fun getAlbumResponse(albumId: Int): ResponseResult<Album> {
        albumDao.getAlbum(albumId)?.let {
            Log.d(LOG_TAG, "Return from local [ROOM ${it}] [PREFS ${albumPrefs.getAlbum(albumId)}]")
            return ResponseResult.Success(it.toAlbum())
        } ?: run {
            val response = retrofitService.getAlbumResponse(albumId).responseResult()
            if (response is ResponseResult.Success) {
                albumPrefs.insert(response.data)
                albumDao.insert(response.data.toAlbumEntity())
                Log.d(LOG_TAG, "Return from api [${response.data}]")
            }
            return response
        }
    }
}

