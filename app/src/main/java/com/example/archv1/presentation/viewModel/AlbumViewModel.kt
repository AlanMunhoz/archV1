package com.example.archv1.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.archv1.data.model.ResponseResult
import com.example.archv1.data.repository.AlbumRepository
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class AlbumViewModel : ViewModel() {

    private val albumsRepository = AlbumRepository()
    private var responseResult = ResponseResult()

    fun getIsUpdating() = responseResult

    /** Retrofit has its own custom dispatchers **/
    val album = liveData {
        val receivedAlbum = albumsRepository.getAlbum(5)
        emit(receivedAlbum)
    }

    fun getSingleAlbum(albumId: Int) = liveData {
        val receivedAlbum = albumsRepository.getAlbum(albumId)
        emit(receivedAlbum)
    }

    fun getSingleAlbumResponse(albumId: Int) = liveData {
        try {
            responseResult.isUpdating.value = true
            val receivedAlbumResponse = albumsRepository.getAlbumResponse(albumId)
            responseResult.messageResult.value = if(receivedAlbumResponse.code() == 200) {
                 "Successfully received"
            } else {
                "Error with code ${receivedAlbumResponse.code()}"
            }
            emit(receivedAlbumResponse)
        } catch (exception: IOException) {
            responseResult.messageResult.value = "Network error"
        } catch (exception: HttpException) {
            responseResult.messageResult.value = "Generic error"
        } finally {
            responseResult.isUpdating.value = false
        }
    }
}