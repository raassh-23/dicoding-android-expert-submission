package com.raassh.core.data.local

import com.raassh.core.data.source.local.entity.MovieEntity
import com.raassh.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.flow

class FakeDao : MovieDao {
    private val movieList = ArrayList<MovieEntity>()

    override fun getAllMovie() = flow {
        emit(movieList)
    }

    override fun getFavoriteMovie() = flow {
        emit(movieList.filter { it.isFavorite })
    }

    override suspend fun insertMovie(movies: List<MovieEntity>) {
        movieList.addAll(movies)
    }

    override fun updateFavoriteMovie(movieId: Int, state: Boolean) {
        val movie = movieList.find { it.id == movieId }
        movie?.isFavorite = state
    }

    override fun searchMovie(query: String) = flow {
        emit(movieList.filter { it.title?.contains(query, true) ?: false })
    }
}