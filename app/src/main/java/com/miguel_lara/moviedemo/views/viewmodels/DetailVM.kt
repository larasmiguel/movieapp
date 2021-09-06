package com.miguel_lara.moviedemo.views.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel_lara.moviedemo.App
import com.miguel_lara.moviedemo.api.ApiClient
import com.miguel_lara.moviedemo.api.ApiInterface
import com.miguel_lara.moviedemo.api.Repository
import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.objects.MovieDetail
import com.miguel_lara.moviedemo.objects.Movies
import com.miguel_lara.moviedemo.objects.Trailer
import kotlinx.coroutines.launch
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
    private var _trailers = MutableLiveData<List<Trailer>>()

    val image: LiveData<String> get() { return _image }
    val title: LiveData<String> get() { return _title }
    val year: LiveData<String> get() { return _year }
    val genre: LiveData<String> get() { return _genre }
    val popularity: LiveData<String> get() { return _popularity }
    val overview: LiveData<String> get() { return _overview }
    val runtime: LiveData<String> get() { return _runtime }
    val link: LiveData<String> get() { return _link }
    val trailers: LiveData<List<Trailer>> get() = _trailers

    fun setMovie(movie: Movie) {
        _movie.value = movie

        viewModelScope.launch {
            val detail = Repository().getMovieDetails(movie.id)
            detail.apply {
                _overview.value = overview
                _runtime.value = "Runtime: $runtime"
                _link.value = homepage
                _trailers.value = trailers
            }
        }
        _image.value = Movie.getFullImageUrl(movie)
        _title.value = "Title: " + movie.title!!
        _year.value = "Year: " + movie.release_date!!
        val genresString = Movie.getGenres(movie)
        _genre.value = "Genre(s): $genresString"
        _popularity.value = "Popularity: " + movie.popularity.toString()
    }
}