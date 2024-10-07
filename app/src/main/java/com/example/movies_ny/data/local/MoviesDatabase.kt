package com.example.movies_ny.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MoviesEntity::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}