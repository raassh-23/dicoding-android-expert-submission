package com.raassh.core.data.source.remote

import android.util.Log
import com.raassh.core.data.source.remote.network.ApiResponse
import com.raassh.core.data.source.remote.network.ApiService
import com.raassh.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllMovie(): Flow<ApiResponse<List<MovieResponse>>> = flow {
        try {
            val response = apiService.getList()
            val dataArray = response.results

            if (dataArray.isNotEmpty()) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    fun searchMovie(query: String) = flow {
        try {
            val response = apiService.searchMovie(query)
            val dataArray = response.results

            if (dataArray.isNotEmpty()) {
                emit(ApiResponse.Success(dataArray))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)
}

