package com.raassh.core.data.source.remote.network

import com.raassh.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET

interface ApiService {
    @GET("popular")
    suspend fun getList(): ListMovieResponse
}
