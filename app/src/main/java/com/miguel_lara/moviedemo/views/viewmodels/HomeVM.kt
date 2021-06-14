package com.miguel_lara.moviedemo.views.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miguel_lara.moviedemo.App
import com.miguel_lara.moviedemo.api.ApiClient
import com.miguel_lara.moviedemo.api.ApiInterface
import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.objects.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeVM: ViewModel() {
    private var _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies
    private var _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> get() = _loading
    private var _error = MutableLiveData<Int>()
    val error: LiveData<Int> get() = _error
    private var page: Int

    init {
        page = 1
        _loading.value = View.GONE
        _error.value = View.GONE
    }

    fun loadData(isRefresh: Boolean = false) {
        if (App.images == null) {
            App.loadInitialConfig()
        }
        if (isRefresh) {
            page++
        }
        else {
            page = 1
        }
        val apiClient = ApiClient.getApiClient().create(ApiInterface::class.java)
        _loading.value = View.VISIBLE
        _error.value = View.GONE
        apiClient.getMovies(page).enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                _loading.value = View.GONE
                _error.value = View.VISIBLE
                _movies.value = ArrayList<Movie>()
            }

            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                _error.value = View.GONE
                _loading.value = View.GONE
                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    if (isRefresh) {
                        val list: MutableList<Movie> = ArrayList()
                        list.addAll(_movies.value!!)
                        list.addAll(res.results!!)
                        _movies.value = list
                    }
                    else {
                        _movies.value = res.results!!
                    }
                }else{
                    _movies.value = ArrayList<Movie>()
                }
            }
        })
    }

}