package com.miguel_lara.moviedemo.api

import com.miguel_lara.moviedemo.objects.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/popular?language=es-MX")
    suspend fun getMoviesPopular(@Query("page") page: Int): Movies

    @GET("movie/now_playing?language=es-MX")
    suspend fun getMoviesNowPlaying(@Query("page") page: Int): Movies

    @GET("movie/{movie_id}?language=es-MX")
    suspend fun getMovieDetail(@Path("movie_id") movie_id: Int): MovieDetail

    @GET("movie/{movie_id}/videos?language=es-MX")
    suspend fun getMovieTrailers(@Path("movie_id") movie_id: Int): Trailers

    @Headers("Cache-Control: public, max-stale=2419200") // 4 weeks
    @GET("configuration")
    fun getConfiguration(): Call<Configuration>

    @GET("genre/movie/list?language=es-MX")
    fun getGenre(): Call<GenresResponse>
}