package com.raassh.core.data.source.local

import com.raassh.core.data.source.local.entity.MovieEntity
import com.raassh.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovie() = movieDao.getAllMovie()

    fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    suspend fun insertMovie(movieList: List<MovieEntity>) =
        movieDao.insertMovie(movieList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }

    fun searchMovie(query: String) = movieDao.searchMovie(query)
}