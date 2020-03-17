package com.example.archv1.presentation.viewModel

import androidx.lifecycle.*
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.domain.usecase.GetAlbum
import com.example.archv1.domain.usecase.GetAlbumResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class AlbumViewModel(
    private val getAlbum: GetAlbum,
    private val getAlbumResponse: GetAlbumResponse
) : ViewModel() {

    private var page: Int = 1
    fun getPage() = page

    private val _requestInProgress = MutableLiveData<Boolean>()
    val requestInProgress: LiveData<Boolean>
        get() = _requestInProgress

    private val _album = MutableLiveData<ResponseResult<Album>>()
    val album: LiveData<ResponseResult<Album>>
        get() = _album

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            try {
                _requestInProgress.postValue(true)
                _album.postValue(getAlbumResponse(albumId))
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
        val receivedAlbum = getAlbum(albumId)
        emit(receivedAlbum)
    }

}