package com.miguel_lara.moviedemo.api

import com.miguel_lara.moviedemo.objects.Configuration
import com.miguel_lara.moviedemo.objects.GenresResponse
import com.miguel_lara.moviedemo.objects.MovieDetail
import com.miguel_lara.moviedemo.objects.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/popular?language=en-US")
    fun getMovies(@Query("page") page: Int): Call<Movies>

    @GET("movie/{movie_id}?language=en-US")
    fun getMovieDetail(@Path("movie_id") movie_id: Int): Call<MovieDetail>

    @Headers("Cache-Control: public, max-stale=2419200") // 4 weeks
    @GET("configuration")
    fun getConfiguration(): Call<Configuration>

    @GET("genre/movie/list?language=en-US")
    fun getGenre(): Call<GenresResponse>
}