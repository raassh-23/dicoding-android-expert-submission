package com.raassh.core.domain.usecase

import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getAllMovie() = movieRepository.getAllMovie()

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movieDomain: MovieDomain, state: Boolean) = movieRepository.setFavoriteMovie(movieDomain, state)
}