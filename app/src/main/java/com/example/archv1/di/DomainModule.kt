package com.example.archv1.di

import com.example.archv1.domain.usecase.GetAlbumListResponseUseCase
import com.example.archv1.domain.usecase.GetAlbumResponseUseCase
import com.example.archv1.domain.usecase.GetAlbumUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAlbumUseCase(get()) }
    factory { GetAlbumResponseUseCase(get()) }
    factory { GetAlbumListResponseUseCase(get()) }
}
