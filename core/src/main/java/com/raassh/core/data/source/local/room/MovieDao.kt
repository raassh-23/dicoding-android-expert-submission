package com.raassh.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raassh.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies where isFavorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movies: List<MovieEntity>)

    @Query("UPDATE movies SET isFavorite = :state WHERE id = :movieId")
    fun updateFavoriteMovie(movieId: Int, state: Boolean)

    @Query("SELECT * FROM movies where title like '%' || :query || '%' OR originalTitle like '%' || :query || '%'")
    fun searchMovie(query: String): Flow<List<MovieEntity>>
}
