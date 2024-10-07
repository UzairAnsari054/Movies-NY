package com.example.movies_ny.presentation

import com.example.movies_ny.domain.Movie

data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
