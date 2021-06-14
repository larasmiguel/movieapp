package com.miguel_lara.moviedemo.views.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miguel_lara.moviedemo.App
import com.miguel_lara.moviedemo.api.ApiClient
import com.miguel_lara.moviedemo.api.ApiInterface
import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.objects.MovieDetail
import com.miguel_lara.moviedemo.objects.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailVM: ViewModel() {
    private var _movie = MutableLiveData<Movie>()
    private var _image = MutableLiveData<String>()
    private var _title = MutableLiveData<String>()
    private var _year = MutableLiveData<String>()
    private var _genre = MutableLiveData<String>()
    private var _popularity = MutableLiveData<String>()
    private var _overview = MutableLiveData<String>()
    private var _runtime = MutableLiveData<String>()
    private var _link = MutableLiveData<String>()

    val image: LiveData<String> get() { return _image }
    val title: LiveData<String> get() { return _title }
    val year: LiveData<String> get() { return _year }
    val genre: LiveData<String> get() { return _genre }
    val popularity: LiveData<String> get() { return _popularity }
    val overview: LiveData<String> get() { return _overview }
    val runtime: LiveData<String> get() { return _runtime }
    val link: LiveData<String> get() { return _link }

    fun setMovie(movie: Movie) {
        _movie.value = movie

        val apiClient = ApiClient.getApiClient().create(ApiInterface::class.java)

        apiClient.getMovieDetail(movie.id).enqueue(object : Callback<MovieDetail> {
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.d("MV", "Error")
            }

            override fun onResponse(
                call: Call<MovieDetail>,
                response: Response<MovieDetail>
            ) {
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    response.body()!!.apply {
                        _overview.value = overview
                        _runtime.value = "Runtime: $runtime"
                        _link.value = homepage
                    }
                }
            }
        })

        _image.value = Movie.getFullImageUrl(movie)
        _title.value = "Title: " + movie.title!!
        _year.value = "Year: " + movie.release_date!!
        val genresString = Movie.getGenres(movie)
        _genre.value = "Genre(s): $genresString"
        _popularity.value = "Popularity: " + movie.popularity.toString()
    }
}