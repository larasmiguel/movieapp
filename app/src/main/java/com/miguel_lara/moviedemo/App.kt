package com.miguel_lara.moviedemo

import android.app.Application
import android.content.res.Resources
import com.miguel_lara.moviedemo.api.ApiClient
import com.miguel_lara.moviedemo.api.ApiInterface
import com.miguel_lara.moviedemo.objects.Configuration
import com.miguel_lara.moviedemo.objects.Genre
import com.miguel_lara.moviedemo.objects.GenresResponse
import com.miguel_lara.moviedemo.objects.Images
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class App : Application() {
    companion object {
        lateinit var instance: Application
        lateinit var my_resources: Resources
        var images: Images? = null
        var genres: List<Genre>? = ArrayList<Genre>()

        fun loadInitialConfig() {
            MainScope().launch {
                val api = ApiClient.getApiClient().create(ApiInterface::class.java)
                images = api.getConfiguration().images
                genres = api.getGenre().genres
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        my_resources = resources
        loadInitialConfig()
    }

}