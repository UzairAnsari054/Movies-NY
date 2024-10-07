package com.example.movies_ny.di

import com.example.movies_ny.data.repository.MovieRepositoryImpl
import com.example.movies_ny.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository
}