package com.example.movies_ny.data.repository

import coil.network.HttpException
import com.example.movies_ny.data.local.MoviesDatabase
import com.example.movies_ny.data.mappers.toMovie
import com.example.movies_ny.data.mappers.toMovieEntity
import com.example.movies_ny.data.remote.MoviesApi
import com.example.movies_ny.domain.Movie
import com.example.movies_ny.domain.MovieRepository
import com.example.movies_ny.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDatabase: MoviesDatabase
) : MovieRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = moviesDatabase.getMoviesDao().getMovieListByCategory(category)
            val shouldLoadLocalMovies = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovies) {
                emit(Resource.Success(data = localMovieList.map { moviesEntity ->
                    moviesEntity.toMovie(category)
                }))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteMovieList = try {
                moviesApi.getMovieList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading Movie list from Api"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading Movie list from Api"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading Movie list from Api"))
                return@flow
            }

            val movieEntity = remoteMovieList.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            moviesDatabase.getMoviesDao().upsertMovieList(movieEntity)
            emit(Resource.Success(
                movieEntity.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovieById(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = moviesDatabase.getMoviesDao().getMovieById(id)

            if (movieEntity != null) {
                emit(Resource.Success(movieEntity.toMovie(movieEntity.category)))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(message = "Error, no such mov"))
            emit(Resource.Loading(false))
        }
    }
}