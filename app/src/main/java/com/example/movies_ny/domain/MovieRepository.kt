package com.example.movies_ny.domain

import com.example.movies_ny.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int,
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovieById(id: Int): Flow<Resource<Movie>>
}