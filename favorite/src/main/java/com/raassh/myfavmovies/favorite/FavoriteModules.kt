package com.raassh.myfavmovies.favorite

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val favoriteModules= module {
    viewModel { FavoriteViewModel(get()) }
}