package com.raassh.myfavmovies.listmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.raassh.core.data.Resource
import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.domain.usecase.MovieUseCase
import com.raassh.core.ui.model.Movie
import com.raassh.core.utils.DataMapper

class ListMovieViewModel(val movieUseCase: MovieUseCase) : ViewModel() {
    val movies = movieUseCase.getAllMovie().asLiveData()
}