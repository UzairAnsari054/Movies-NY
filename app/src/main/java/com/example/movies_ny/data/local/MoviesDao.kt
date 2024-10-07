package com.example.movies_ny.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Upsert
    suspend fun upsertMovieList(moviesList: List<MoviesEntity>)

    @Query("SELECT * FROM movies_table WHERE id=:id")
    suspend fun getMovieById(id: Int): MoviesEntity

    @Query("SELECT * FROM MOVIES_TABLE WHERE category=:category")
    suspend fun getMovieListByCategory(category: String): List<MoviesEntity>
}