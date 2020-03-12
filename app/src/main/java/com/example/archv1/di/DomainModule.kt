package com.example.archv1.di

import com.example.archv1.domain.usecase.GetAlbum
import com.example.archv1.domain.usecase.GetAlbumResponse
import org.koin.dsl.module

val domainModule = module {
    factory { GetAlbum(get()) }
    factory { GetAlbumResponse(get()) }
}