package com.example.archv1.domain.usecase

import com.example.archv1.domain.repository.AlbumRepository

class GetAlbumResponse(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(albumId: Int) = albumRepository.getAlbumResponse(albumId)
}