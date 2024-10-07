package com.example.movies_ny.di

import android.app.Application
import androidx.room.Room
import com.example.movies_ny.data.local.MoviesDatabase
import com.example.movies_ny.data.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideMovieApi(): MoviesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MoviesApi.BASE_URL)
            .client(client)
            .build()
            .create(MoviesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(application: Application): MoviesDatabase {
        return Room.databaseBuilder(
            application,
            MoviesDatabase::class.java,
            "movie_db"
        ).build()
    }
}