package com.miguel_lara.moviedemo.views.viewmodels

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
import com.miguel_lara.moviedemo.objects.Movies
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeVM: ViewModel() {
    private var listNowPlaying = ArrayList<Movie>()
    private var listPopular = ArrayList<Movie>()
    private var _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies
    private var _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> get() = _loading
    private var _error = MutableLiveData<Int>()
    val error: LiveData<Int> get() = _error
    private var type = 0
    private var pageNowPlaying = 1
    private var pageMostPopular = 1

    init {
        _loading.value = View.GONE
        _error.value = View.GONE
    }

    fun changeType(newType: Int) {
        type = newType
    }

    private fun loadNowPlaying(isRefresh: Boolean) {
        if (isRefresh) {
            pageNowPlaying++
        }
        _loading.value = View.VISIBLE
        _error.value = View.GONE
        if (isRefresh || listNowPlaying.size == 0) {
            viewModelScope.launch {
                val movies = Repository().getNowPlayingMovies(pageNowPlaying)
                listNowPlaying.addAll(movies)
                _movies.value = listNowPlaying
                _loading.value = View.GONE
            }
        }
        else {
            _movies.value = listNowPlaying
            _loading.value = View.GONE
        }
    }

    private fun loadMosPopular(isRefresh: Boolean) {
        if (isRefresh) {
            pageMostPopular++
        }
        _loading.value = View.VISIBLE
        _error.value = View.GONE
        if (isRefresh || listPopular.size == 0) {
            viewModelScope.launch {
                val movies = Repository().getMostPopularMovies(pageMostPopular)
                listPopular.addAll(movies)
                _movies.value = listPopular
                _loading.value = View.GONE
            }
        }
        else {
            _movies.value = listPopular
            _loading.value = View.GONE
        }
    }

    fun loadData(isRefresh: Boolean = false) {
        if (type == 0) {
            loadNowPlaying(isRefresh)
        }
        else {
            loadMosPopular(isRefresh)
        }
    }

}