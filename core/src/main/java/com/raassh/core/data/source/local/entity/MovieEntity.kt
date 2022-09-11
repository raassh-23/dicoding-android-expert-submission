package com.raassh.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    val id: Int,

    val overview: String,

    val originalLanguage: String,

    val originalTitle: String,

    val video: Boolean,

    val title: String,

    val genreIds: String,

    val posterPath: String,

    val backdropPath: String,

    val releaseDate: String,

    val popularity: Double,

    val voteAverage: Double,

    val adult: Boolean,

    val voteCount: Int,

    var isFavorite: Boolean = false
)
