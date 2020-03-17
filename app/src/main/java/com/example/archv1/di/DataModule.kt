package com.example.archv1.di

import com.example.archv1.data.network.RetrofitProvider.Companion.provideAlbumService
import com.example.archv1.data.network.RetrofitProvider.Companion.provideHttpClient
import com.example.archv1.data.network.RetrofitProvider.Companion.provideLoginInterceptor
import com.example.archv1.data.network.RetrofitProvider.Companion.providePlaceHolderApi
import com.example.archv1.data.preferences.SharedPrefs
import com.example.archv1.data.repository.AlbumRepositoryImpl
import com.example.archv1.domain.repository.AlbumRepository
import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { provideLoginInterceptor() }
    single { provideHttpClient(get()) }
    single { providePlaceHolderApi(get()) }
    single { provideAlbumService(get()) }
    single { SharedPrefs(get()) }
    single <AlbumRepository> { AlbumRepositoryImpl(get(), get()) }
    viewModel { AlbumViewModel(get(), get()) }
}