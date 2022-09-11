package com.raassh.core.data

import com.raassh.core.data.source.remote.RemoteDataSource
import com.raassh.core.data.source.remote.network.ApiResponse
import com.raassh.core.data.source.remote.response.MovieResponse
import com.raassh.core.domain.model.Movie
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

    override fun getAllTourism(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllTourism().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()
//                 true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllTourism()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTourism(movieList)
            }
        }.asFlow()

    override fun getFavoriteTourism(): Flow<List<Movie>> {
        return localDataSource.getFavoriteTourism().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteTourism(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(movieEntity, state) }
    }
}

