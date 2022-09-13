package com.raassh.myfavmovies.home

import androidx.lifecycle.*
import com.raassh.core.domain.usecase.MovieUseCase

class HomeViewModel(val movieUseCase: MovieUseCase) : ViewModel() {
    var query = MutableLiveData<String>()

    init {
        query.value = ""
    }

    val movies = Transformations.switchMap(query) {
        if (it.isEmpty()) {
            movieUseCase.getAllMovie().asLiveData()
        } else {
            movieUseCase.searchMovie(it).asLiveData()
        }
    }

    fun setQuery(query: String) {
        this.query.value = query
    }
}