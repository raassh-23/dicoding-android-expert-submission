package com.raassh.myfavmovies.detail

import androidx.lifecycle.ViewModel
import com.raassh.core.domain.usecase.MovieUseCase
import com.raassh.core.ui.model.Movie

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun setFavoriteMovie(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        movieUseCase.setFavoriteMovie(movie.id, movie.isFavorite)
    }

}