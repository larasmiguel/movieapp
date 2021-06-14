package com.miguel_lara.moviedemo

import android.app.Application
import android.content.res.Resources
import com.miguel_lara.moviedemo.api.ApiClient
import com.miguel_lara.moviedemo.api.ApiInterface
import com.miguel_lara.moviedemo.objects.Configuration
import com.miguel_lara.moviedemo.objects.Genre
import com.miguel_lara.moviedemo.objects.GenresResponse
import com.miguel_lara.moviedemo.objects.Images
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
            val api = ApiClient.getApiClient().create(ApiInterface::class.java)
            api.getConfiguration().enqueue(object : Callback<Configuration> {
                override fun onFailure(call: Call<Configuration>, t: Throwable) {
                    images = null
                }

                override fun onResponse(
                    call: Call<Configuration>,
                    response: Response<Configuration>
                ) {

                    val res = response.body()
                    if (response.code() == 200 &&  res!=null){
                        images = res.images
                    }else{
                        images = null
                    }
                }
            })
            api.getGenre().enqueue(object : Callback<GenresResponse> {
                override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                    genres = null
                }

                override fun onResponse(
                    call: Call<GenresResponse>,
                    response: Response<GenresResponse>
                ) {

                    val res = response.body()
                    if (response.code() == 200 &&  res!=null){
                        genres = res.genres
                    }else{
                        genres = null
                    }
                }
            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        my_resources = resources
        loadInitialConfig()
    }

}