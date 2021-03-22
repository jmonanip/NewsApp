package com.mydomain.newsapplication.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsServiceClient {
    private const val BASE_URL = "https://api.nytimes.com/svc/search/v2/"

    private val httpClient = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}
