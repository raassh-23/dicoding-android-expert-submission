package com.raassh.core.di

import com.raassh.core.data.MovieRepository
import com.raassh.core.data.local.FakeDao
import com.raassh.core.data.remote.FakeApiService
import com.raassh.core.data.source.local.LocalDataSource
import com.raassh.core.data.source.local.room.MovieDao
import com.raassh.core.data.source.remote.RemoteDataSource
import com.raassh.core.data.source.remote.network.ApiService
import com.raassh.core.utils.AppExecutors
import org.koin.dsl.module

val testModules = module {
    factory<MovieDao> {
        FakeDao()
    }

    single<ApiService> {
        FakeApiService()
    }

    single {
        MovieRepository(RemoteDataSource(FakeApiService()), LocalDataSource(FakeDao()), AppExecutors())
    }


}