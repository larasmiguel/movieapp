package com.miguel_lara.moviedemo.api

import android.os.Build
import com.google.gson.GsonBuilder
import com.miguel_lara.moviedemo.App
import com.miguel_lara.moviedemo.R
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response  {
        val originalRequest = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", App.my_resources.getString(R.string.api_key))
            .build()

        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

class ApiClient {
    companion object{
        private var retrofit: Retrofit?=null
        fun getApiClient(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(App.my_resources.getString(R.string.base_url))
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }
    }
}