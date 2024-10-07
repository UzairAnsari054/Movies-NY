package com.example.movies_ny.data.remote

import com.example.movies_ny.data.remote.model.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieListDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "45ce46a99d8b61da5c6f04124fc1c03d"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}


//https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&api_key=45ce46a99d8b61da5c6f04124fc1c03d