package com.example.archv1.domain.usecase

import com.example.archv1.domain.repository.AlbumRepository

class GetAlbumListResponseUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke() = albumRepository.getAlbumListResponse()
}
