package com.raassh.myfavmovies.listmovie

import android.util.Log
import androidx.lifecycle.*
import com.raassh.core.data.Resource
import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.domain.usecase.MovieUseCase
import com.raassh.core.ui.model.Movie
import com.raassh.core.utils.DataMapper

class ListMovieViewModel(val movieUseCase: MovieUseCase) : ViewModel() {
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