package com.example.archv1

import android.app.Application
import com.example.archv1.di.appComponent
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, appComponent)
    }
}