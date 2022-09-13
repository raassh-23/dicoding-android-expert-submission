package com.raassh.core.data.source.local

import com.raassh.core.data.source.local.entity.MovieEntity
import com.raassh.core.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovie() = movieDao.getAllMovie()

    fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    suspend fun insertMovie(movieList: List<MovieEntity>) =
        movieDao.insertMovie(movieList)

    fun setFavoriteMovie(movieId: Int, newState: Boolean) =
        movieDao.updateFavoriteMovie(movieId, newState)

    fun searchMovie(query: String) = movieDao.searchMovie(query)
}