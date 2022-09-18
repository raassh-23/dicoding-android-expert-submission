package com.raassh.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    val id: Int,

    val overview: String? = null,

    val originalLanguage: String? = null,

    val originalTitle: String? = null,

    val video: Boolean? = null,

    val title: String? = null,

    val genreIds: String? = null,

    val posterPath: String? = null,

    val backdropPath: String? = null,

    val releaseDate: String? = null,

    val popularity: Double? = null,

    val voteAverage: Double? = null,

    val adult: Boolean? = null,

    val voteCount: Int? = null,

    var isFavorite: Boolean = false
)
