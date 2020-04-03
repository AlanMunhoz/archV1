package com.example.archv1.data.util

import com.example.archv1.MainApplication
import com.example.archv1.data.preferences.AlbumPrefs
import com.example.archv1.data.room.AlbumEntity
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import retrofit2.Response

fun <T: Any> Response<T>.responseResult() : ResponseResult<T> {
    return this.body()?.let {
        ResponseResult.Success(it)
    } ?: run {
        ResponseResult.Failure("HttpCode: ${this.code()}")
    }
}

fun AlbumPrefs.getContext() = MainApplication.getContext()

fun Album.toAlbumEntity() = AlbumEntity(
    id = this.id,
    title = this.title,
    userId = this.userId,
    lstMusics = listOf("musicTest1", "musicTest2")
)

fun AlbumEntity.toAlbum() = Album(
    id = this.id,
    title = this.title,
    userId = this.userId
)
