package com.raassh.core.domain.usecase

import android.util.Log
import com.raassh.core.data.Resource
import com.raassh.core.domain.repository.IMovieRepository
import com.raassh.core.ui.model.Movie
import com.raassh.core.utils.DataMapper
import kotlinx.coroutines.flow.transform

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getAllMovie() = movieRepository.getAllMovie().transform { value ->
        if (value is Resource.Success) {
            val data = DataMapper.mapDomainToPresentation(value.data)
            emit(Resource.Success(data))
        } else {
            emit(value as Resource<List<Movie>>)
        }
    }

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie().transform {
        emit(DataMapper.mapDomainToPresentation(it))
    }

    override fun setFavoriteMovie(movieId: Int, state: Boolean) =
        movieRepository.setFavoriteMovie(movieId, state)

    override fun searchMovie(query: String) = movieRepository.searchMovie(query)
        .transform { value ->
            if (value is Resource.Success) {
                val data = DataMapper.mapDomainToPresentation(value.data)
                emit(Resource.Success(data))
            } else {
                emit(value as Resource<List<Movie>>)
            }
        }
}