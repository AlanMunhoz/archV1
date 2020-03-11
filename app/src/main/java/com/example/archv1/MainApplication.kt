package com.example.archv1

import android.app.Application
import com.example.archv1.di.dataModule
import com.example.archv1.di.domainModule
import com.example.archv1.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}