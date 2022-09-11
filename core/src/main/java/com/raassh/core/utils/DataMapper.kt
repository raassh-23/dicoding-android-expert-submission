package com.raassh.core.utils

import com.raassh.core.domain.model.Movie
import com.raassh.core.data.source.local.entity.MovieEntity
import com.raassh.core.data.source.remote.response.MovieResponse

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val tourismList = ArrayList<MovieEntity>()
        input.map {
            val tourism = MovieEntity(
                tourismId = it.id,
                description = it.description,
                name = it.name,
                address = it.address,
                latitude = it.latitude,
                longitude = it.longitude,
                like = it.like,
                image = it.image,
                isFavorite = false
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                tourismId = it.tourismId,
                description = it.description,
                name = it.name,
                address = it.address,
                latitude = it.latitude,
                longitude = it.longitude,
                like = it.like,
                image = it.image,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        tourismId = input.tourismId,
        description = input.description,
        name = input.name,
        address = input.address,
        latitude = input.latitude,
        longitude = input.longitude,
        like = input.like,
        image = input.image,
        isFavorite = input.isFavorite
    )
}