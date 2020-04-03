package com.example.archv1.di

import com.example.archv1.data.network.RetrofitProvider.Companion.provideAlbumService
import com.example.archv1.data.network.RetrofitProvider.Companion.provideHttpClient
import com.example.archv1.data.network.RetrofitProvider.Companion.provideLoginInterceptor
import com.example.archv1.data.network.RetrofitProvider.Companion.providePlaceHolderApi
import com.example.archv1.data.preferences.AlbumPrefs
import com.example.archv1.data.repository.AlbumRepositoryImpl
import com.example.archv1.data.room.AlbumDatabase
import com.example.archv1.data.room.AlbumDatabase.Companion.albumProvider
import com.example.archv1.domain.repository.AlbumRepository
import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { provideLoginInterceptor() }
    single { provideHttpClient(get()) }
    single { providePlaceHolderApi(get()) }
    single { provideAlbumService(get()) }
    single { AlbumPrefs(get()) }
    single { albumProvider(androidApplication()) }
    single { get<AlbumDatabase>().albumDao }
    single<AlbumRepository> { AlbumRepositoryImpl(get(), get(), get()) }
    viewModel { AlbumViewModel(get(), get()) }
}