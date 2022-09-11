package com.raassh.myfavmovies

import android.app.Application
import com.raassh.core.di.databaseModule
import com.raassh.core.di.networkModule
import com.raassh.core.di.repositoryModule
import com.raassh.myfavmovies.di.useCaseModule
import com.raassh.myfavmovies.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(listOf(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            ))
        }
    }
}