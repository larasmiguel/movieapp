package com.miguel_lara.moviedemo.api

import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.objects.MovieDetail
import com.miguel_lara.moviedemo.objects.Movies

interface IRepository {
    suspend fun getNowPlayingMovies(page: Int): List<Movie>
    suspend fun getMostPopularMovies(page: Int): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetail
}