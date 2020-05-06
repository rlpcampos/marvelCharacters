package com.example.marvelcharacters

import android.app.Application
import com.example.marvelcharacters.di.networkModule
import com.example.marvelcharacters.di.repositoryModule
import com.example.marvelcharacters.di.viewModelModule
import com.example.marvelcharacters.network.ConnectHandler
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        ConnectHandler.registerCallback(this)
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CustomApplication)
            modules(listOf(viewModelModule, networkModule, repositoryModule))
        }
    }

    override fun onTerminate() {
        stopKoin()
        ConnectHandler.unRegisterCallback(this)
        super.onTerminate()
    }
}