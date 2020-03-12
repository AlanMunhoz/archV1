package com.example.archv1.di

import com.example.archv1.data.RetrofitProvider.Provider.provideAlbumService
import com.example.archv1.data.RetrofitProvider.Provider.provideHttpClient
import com.example.archv1.data.RetrofitProvider.Provider.provideLoginInterceptor
import com.example.archv1.data.RetrofitProvider.Provider.providePlaceHolderApi
import com.example.archv1.data.repository.AlbumRepository
import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { provideLoginInterceptor() }
    single { provideHttpClient(get()) }
    single { providePlaceHolderApi(get()) }
    single { provideAlbumService(get()) }
    single { AlbumRepository(get()) }
    viewModel { AlbumViewModel(get()) }
}