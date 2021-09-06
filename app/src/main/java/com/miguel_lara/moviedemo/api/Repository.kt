package com.miguel_lara.moviedemo.api

import com.miguel_lara.moviedemo.App
import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.objects.MovieDetail

class Repository: IRepository {

    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        if (App.images == null) {
            App.loadInitialConfig()
        }
        val apiClient = ApiClient.getApiClient().create(ApiInterface::class.java)
        var movies = apiClient.getMoviesNowPlaying(page)
        return movies.results!!
    }

    override suspend fun getMostPopularMovies(page:Int): List<Movie> {
        if (App.images == null) {
            App.loadInitialConfig()
        }
        val apiClient = ApiClient.getApiClient().create(ApiInterface::class.java)
        var movies = apiClient.getMoviesPopular(page)
        return movies.results!!
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetail {
        val apiClient = ApiClient.getApiClient().create(ApiInterface::class.java)
        val detail = apiClient.getMovieDetail(movieId)
        val trailers = apiClient.getMovieTrailers(movieId)
        if (trailers.results == null) {
            detail.trailers = ArrayList()
        }
        else {
            detail.trailers = trailers.results!!
        }
        return detail
    }

}