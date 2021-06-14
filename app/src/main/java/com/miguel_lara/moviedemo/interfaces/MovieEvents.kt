package com.miguel_lara.moviedemo.interfaces

import com.miguel_lara.moviedemo.objects.Movie

interface MovieEvents {
    fun onMovieClick(movie: Movie)
}