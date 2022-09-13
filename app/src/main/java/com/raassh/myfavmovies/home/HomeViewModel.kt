package com.raassh.myfavmovies.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.raassh.core.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val query = MutableLiveData<String>()

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