package com.example.archv1.di

import com.example.archv1.presentation.viewModel.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { AlbumViewModel(get(), get(), get()) }
}
