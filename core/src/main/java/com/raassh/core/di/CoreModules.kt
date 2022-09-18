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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI

val databaseModule = module {
    factory {
        get<MovieDatabase>().tourismDao()
    }

    single {
        val factory = SupportFactory(SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE.toCharArray()))

        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "movies.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = URI(BuildConfig.BASE_URL).host
        val certificatePinner = okhttp3.CertificatePinner.Builder()
            .add(hostname, "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()
                val newUrl = original.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
                it.proceed(original.newBuilder().url(newUrl).build())
            }
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            )
            .certificatePinner(certificatePinner)
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