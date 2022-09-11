package com.raassh.core.utils

import com.raassh.core.BuildConfig
import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.data.source.local.entity.MovieEntity
import com.raassh.core.data.source.remote.response.MovieResponse
import com.raassh.core.ui.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = it.genreIds.joinToString(", "),
                posterPath = BuildConfig.BASE_IMAGE_URL + it.posterPath,
                backdropPath = BuildConfig.BASE_IMAGE_URL + it.backdropPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                adult = it.adult,
                voteCount = it.voteCount,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<MovieDomain> =
        input.map {
            MovieDomain(
                id = it.id,
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = it.genreIds.split(", ")
                    .map { genre -> genre.toInt() },
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                adult = it.adult,
                voteCount = it.voteCount,
                isFavorite = false
            )
        }

    fun mapDomainToEntity(input: MovieDomain) = MovieEntity(
        id = input.id,
        overview = input.overview,
        originalLanguage = input.originalLanguage,
        originalTitle = input.originalTitle,
        video = input.video,
        title = input.title,
        genreIds = input.genreIds.joinToString(", "),
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        releaseDate = input.releaseDate,
        popularity = input.popularity,
        voteAverage = input.voteAverage,
        adult = input.adult,
        voteCount = input.voteCount,
        isFavorite = false
    )

    fun mapDomainToPresentation(input: MovieDomain) = Movie(
        id = input.id,
        overview = input.overview,
        originalLanguage = input.originalLanguage,
        title = input.title,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        isFavorite = false
    )
}