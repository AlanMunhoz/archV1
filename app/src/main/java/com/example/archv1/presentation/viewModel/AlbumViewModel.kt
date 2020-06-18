package com.example.archv1.presentation.viewModel

import androidx.lifecycle.*
import com.example.archv1.domain.model.Album
import com.example.archv1.domain.model.ResponseResult
import com.example.archv1.domain.usecase.GetAlbumListResponseUseCase
import com.example.archv1.domain.usecase.GetAlbumUseCase
import com.example.archv1.domain.usecase.GetAlbumResponseUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class AlbumViewModel(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val getAlbumResponseUseCase: GetAlbumResponseUseCase,
    private val getAlbumListResponseUseCase: GetAlbumListResponseUseCase
) : ViewModel() {

    private var page: Int = 1
    fun getPage() = page

    private val _requestInProgress = MutableLiveData<Boolean>()
    val requestInProgress: LiveData<Boolean>
        get() = _requestInProgress

    private val _album = MutableLiveData<ResponseResult<Album>>()
    val album: LiveData<ResponseResult<Album>>
        get() = _album

    private val _albumList = MutableLiveData<ResponseResult<List<Album>>>()
    val albumList: LiveData<ResponseResult<List<Album>>>
        get() = _albumList

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            try {
                _requestInProgress.postValue(true)
                _album.postValue(getAlbumResponseUseCase(albumId))
                page++
            } catch (e: Exception) {
                _album.postValue(ResponseResult.Failure("ExceptionMessage: ${e.message}"))
                e.printStackTrace()
            } finally {
                _requestInProgress.postValue(false)
            }
        }
    }

    fun loadAlbums() {
        viewModelScope.launch {
            try {
                _requestInProgress.postValue(true)
                val list = getAlbumListResponseUseCase()
                _albumList.postValue(list)
                page = (list as ResponseResult.Success).data.size + 1
            } catch (e: Exception) {
                _albumList.postValue(ResponseResult.Failure("ExceptionMessage: ${e.message}"))
                e.printStackTrace()
            } finally {
                _requestInProgress.postValue(false)
            }
        }
    }

    /** Retrofit has its own custom dispatchers **/
    fun getSingleAlbum(albumId: Int) = liveData {
        val receivedAlbum = getAlbumUseCase(albumId)
        emit(receivedAlbum)
    }

}