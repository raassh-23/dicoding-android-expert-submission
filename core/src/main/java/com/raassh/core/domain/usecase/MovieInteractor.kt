package com.raassh.core.domain.usecase

import com.raassh.core.domain.model.Movie
import com.raassh.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getAllTourism() = movieRepository.getAllTourism()

    override fun getFavoriteTourism() = movieRepository.getFavoriteTourism()

    override fun setFavoriteTourism(movie: Movie, state: Boolean) = movieRepository.setFavoriteTourism(movie, state)
}