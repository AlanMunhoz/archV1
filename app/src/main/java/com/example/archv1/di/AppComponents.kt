package com.example.archv1.di

import org.koin.dsl.module.Module

val appComponent: List<Module> = listOf(presentationModule, domainModule, dataModule)