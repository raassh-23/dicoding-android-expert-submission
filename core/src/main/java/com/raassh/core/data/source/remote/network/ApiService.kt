package com.raassh.core.data.source.remote.network

import com.raassh.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getList(): ListMovieResponse

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): ListMovieResponse
}
