package com.raassh.core.domain.usecase

import com.raassh.core.data.Resource
import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.ui.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovie(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<MovieDomain>>
    fun setFavoriteMovie(movieId: Int, state: Boolean)
    fun searchMovie(query: String): Flow<Resource<List<Movie>>>
}