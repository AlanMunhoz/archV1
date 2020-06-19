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
        const val LIST_MAX_QTY_REQUEST = 50
    }

    override suspend fun getAlbum(albumId: Int) = retrofitService.getAlbum(albumId)

    override suspend fun getAlbumResponse(albumId: Int): ResponseResult<Album> {
        albumDao.getAlbum(albumId)?.let {
            Log.d(LOG_TAG, "Return from local [ROOM $it] [PREFS ${albumPrefs.getAlbum(albumId)}]")
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

    override suspend fun getAlbumListResponse(): ResponseResult<List<Album>> {
        val list = albumDao.getAll()
        return if (list.isNotEmpty()) {
            Log.d(LOG_TAG, "Return from local [ROOM ${list.size}] [PREFS ${albumPrefs.getAll()?.size}]")
            ResponseResult.Success(list.map { it.toAlbum() })
        } else {
            val response = retrofitService.getAlbumListResponse().responseResult()
            if (response is ResponseResult.Success) {

                /** Limit of items quantity in request, allowing new single requests afterwards **/
                val responseFiltered =
                    ResponseResult.Success(response.data.filter { it.id < LIST_MAX_QTY_REQUEST })

                albumPrefs.saveAlbumList(ArrayList(responseFiltered.data))
                albumDao.setList(responseFiltered.data.map { it.toAlbumEntity() })
                Log.d(LOG_TAG, "Return from api [${responseFiltered.data}]")
                responseFiltered
            } else {
                response
            }
        }
    }
}
