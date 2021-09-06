package com.miguel_lara.moviedemo.interfaces

import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.objects.Trailer

interface MovieEvents {
    fun onMovieClick(movie: Movie)
}

interface TrailerEvents {
    fun onTrailerClick(trailer: Trailer)
}