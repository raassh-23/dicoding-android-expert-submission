package com.raassh.core.domain.usecase

import com.raassh.core.data.Resource
import com.raassh.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllTourism(): Flow<Resource<List<Movie>>>
    fun getFavoriteTourism(): Flow<List<Movie>>
    fun setFavoriteTourism(movie: Movie, state: Boolean)
}