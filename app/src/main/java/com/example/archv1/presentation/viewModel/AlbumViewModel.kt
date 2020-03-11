package com.example.archv1.presentation.viewModel

import androidx.lifecycle.*
import com.example.archv1.data.model.*
import com.example.archv1.data.repository.AlbumRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AlbumViewModel(private val albumsRepository: AlbumRepository) : ViewModel() {

    private var page: Int = 0
    fun getPage() = page

    private val _requestInProgress = MutableLiveData<Boolean>()
    val requestInProgress: LiveData<Boolean>
        get() = _requestInProgress

    private val _album = MutableLiveData<ResponseResult<Album>>()
    val album2: LiveData<ResponseResult<Album>>
        get() = _album

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            try {
                _requestInProgress.postValue(true)
                val response = albumsRepository.getAlbumResponse(albumId)
                response.body()?.let {
                    _album.postValue(ResponseResult.Success(it))
                } ?: run {
                    _album.postValue(ResponseResult.Failure("HttpCode: ${response.code()}"))
                }
                page++
            } catch (e: Exception) {
                _album.postValue(ResponseResult.Failure("ExceptionMessage: ${e.message}"))
                e.printStackTrace()
            } finally {
                _requestInProgress.postValue(false)
            }
        }
    }

    /** Retrofit has its own custom dispatchers **/
    fun getSingleAlbum(albumId: Int) = liveData {
        val receivedAlbum = albumsRepository.getAlbum(albumId)
        emit(receivedAlbum)
    }

}