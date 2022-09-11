package com.raassh.core.domain.repository

import com.raassh.core.data.Resource
import com.raassh.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getAllTourism(): Flow<Resource<List<Movie>>>

    fun getFavoriteTourism(): Flow<List<Movie>>

    fun setFavoriteTourism(movie: Movie, state: Boolean)

}
