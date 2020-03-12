package com.example.archv1.domain.usecase

import com.example.archv1.data.model.Album
import com.example.archv1.domain.repository.AlbumRepository
import retrofit2.Response

class GetAlbumResponse(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(albumId: Int): Response<Album> = albumRepository.getAlbumResponse(albumId)
}