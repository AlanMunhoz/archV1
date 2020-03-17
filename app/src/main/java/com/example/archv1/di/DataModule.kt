package com.example.archv1.di

import com.example.archv1.data.network.RetrofitProvider.Provider.provideAlbumService
import com.example.archv1.data.network.RetrofitProvider.Provider.provideHttpClient
import com.example.archv1.data.network.RetrofitProvider.Provider.provideLoginInterceptor
import com.example.archv1.data.network.RetrofitProvider.Provider.providePlaceHolderApi
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
    single <AlbumRepository> { AlbumRepositoryImpl(get()) }
    viewModel { AlbumViewModel(get(), get()) }
}