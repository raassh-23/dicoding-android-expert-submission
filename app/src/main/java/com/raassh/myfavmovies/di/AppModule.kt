package com.raassh.myfavmovies.di

import com.raassh.core.domain.usecase.MovieInteractor
import com.raassh.core.domain.usecase.MovieUseCase
import com.raassh.myfavmovies.listmovie.ListMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> {
        MovieInteractor(get())
    }
}

val viewModelModule = module {
    viewModel {
        ListMovieViewModel(get())
    }
}