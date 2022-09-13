package com.raassh.myfavmovies.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.raassh.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favMovies = movieUseCase.getFavoriteMovie().asLiveData()
}