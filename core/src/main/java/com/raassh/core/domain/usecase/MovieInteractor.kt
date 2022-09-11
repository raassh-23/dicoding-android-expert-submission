package com.raassh.core.domain.usecase

import com.raassh.core.data.Resource
import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.domain.repository.IMovieRepository
import com.raassh.core.ui.model.Movie
import com.raassh.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getAllMovie() = movieRepository.getAllMovie().transform { value ->
        if (value is Resource.Success) {
            val data = DataMapper.mapDomainToPresentation(value.data)
            emit(Resource.Success(data))
        } else {
            emit(value as Resource<List<Movie>>)
        }
    }

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movieDomain: MovieDomain, state: Boolean) =
        movieRepository.setFavoriteMovie(movieDomain, state)
}