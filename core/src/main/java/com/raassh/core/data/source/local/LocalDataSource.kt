package com.raassh.core.data.source.local

import com.raassh.core.data.source.local.entity.MovieEntity
import com.raassh.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllTourism(): Flow<List<MovieEntity>> = movieDao.getAllTourism()

    fun getFavoriteTourism(): Flow<List<MovieEntity>> = movieDao.getFavoriteTourism()

    suspend fun insertTourism(tourismList: List<MovieEntity>) =
        movieDao.insertTourism(tourismList)

    fun setFavoriteTourism(tourism: MovieEntity, newState: Boolean) {
        tourism.isFavorite = newState
        movieDao.updateFavoriteTourism(tourism)
    }
}