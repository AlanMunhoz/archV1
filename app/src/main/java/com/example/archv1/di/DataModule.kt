package com.example.archv1.di

import com.example.archv1.data.repository.AlbumRepository
import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { AlbumRepository() }
    viewModel { AlbumViewModel(get()) }
}