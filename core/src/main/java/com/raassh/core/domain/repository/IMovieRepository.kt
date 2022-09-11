package com.raassh.core.domain.repository

import com.raassh.core.data.Resource
import com.raassh.core.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getAllMovie(): Flow<Resource<List<MovieDomain>>>

    fun getFavoriteMovie(): Flow<List<MovieDomain>>

    fun setFavoriteMovie(movieDomain: MovieDomain, state: Boolean)

}
