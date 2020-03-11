package com.example.archv1.di

import com.example.archv1.data.RetrofitProvider.Factory.provideAlbumService
import com.example.archv1.data.RetrofitProvider.Factory.provideHttpClient
import com.example.archv1.data.RetrofitProvider.Factory.provideLoginInterceptor
import com.example.archv1.data.repository.AlbumRepository
import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    /** Services dependencies **/
    single { provideLoginInterceptor() }
    single { provideHttpClient(get()) }
    single { provideAlbumService(get()) }
    /** Repository dependency **/
    single { AlbumRepository(get()) }
    /** ViewModel dependency **/
    viewModel { AlbumViewModel(get()) }
}