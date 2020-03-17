package com.example.archv1

import android.app.Application
import com.example.archv1.di.dataModule
import com.example.archv1.di.domainModule
import com.example.archv1.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainApplication : Application() {

    companion object {
        var mainApplication : Application? = null
        fun getContext() = mainApplication?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        mainApplication = this

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}