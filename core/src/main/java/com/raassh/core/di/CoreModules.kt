package com.raassh.core.di

import androidx.room.Room
import com.raassh.core.BuildConfig
import com.raassh.core.data.MovieRepository
import com.raassh.core.data.source.local.LocalDataSource

import com.raassh.core.data.source.local.room.MovieDatabase
import com.raassh.core.data.source.remote.RemoteDataSource
import com.raassh.core.data.source.remote.network.ApiService
import com.raassh.core.domain.repository.IMovieRepository
import com.raassh.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<MovieDatabase>().tourismDao()
    }

    single {
        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "movies.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor {
                val original = it.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY).build()
                it.proceed(original.newBuilder().url(url).build())
            }
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(get())
    }
    single {
        RemoteDataSource(get())
    }
    factory {
        AppExecutors()
    }
    single<IMovieRepository> {
        MovieRepository(get(), get(), get())
    }
}