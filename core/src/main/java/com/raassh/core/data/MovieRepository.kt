package com.raassh.core.data

import com.raassh.core.data.source.remote.RemoteDataSource
import com.raassh.core.data.source.remote.network.ApiResponse
import com.raassh.core.data.source.remote.response.MovieResponse
import com.raassh.core.domain.model.MovieDomain
import com.raassh.core.domain.repository.IMovieRepository
import com.raassh.core.utils.AppExecutors
import com.raassh.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: com.raassh.core.data.source.local.LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<MovieDomain>>> =
        object : NetworkBoundResource<List<MovieDomain>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<MovieDomain>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieDomain>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<MovieDomain>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movieDomain: MovieDomain, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movieDomain)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }
}

