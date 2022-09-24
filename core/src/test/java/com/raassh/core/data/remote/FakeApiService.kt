package com.raassh.core.data.remote

import com.raassh.core.data.source.remote.network.ApiService
import com.raassh.core.data.source.remote.response.ListMovieResponse
import com.raassh.core.data.source.remote.response.MovieResponse

class FakeApiService : ApiService {
    override suspend fun getList(): ListMovieResponse {
        val movies = mutableListOf<MovieResponse>()

        for (i in 1..10) {
            val movie = MovieResponse(
                id = i,
                title = "Movie $i",
            )
            movies.add(movie)
        }

        return ListMovieResponse(1, 1, movies, 10)
    }

    override suspend fun searchMovie(query: String) =
        ListMovieResponse(1, 1, listOf(MovieResponse(id = 11, title = query)), 10)
}