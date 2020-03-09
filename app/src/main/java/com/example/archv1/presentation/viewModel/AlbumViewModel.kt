package com.example.archv1.presentation.viewModel

import androidx.lifecycle.*
import com.example.archv1.data.model.Album
import com.example.archv1.data.model.ResponseResult
import com.example.archv1.data.repository.AlbumRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class AlbumViewModel : ViewModel() {

    private val albumsRepository = AlbumRepository()

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private var responseResult = ResponseResult()

    fun getIsUpdating() = responseResult

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            try {
                val result = albumsRepository.getAlbum(albumId)
                _album.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /** Retrofit has its own custom dispatchers **/
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