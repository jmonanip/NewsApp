package com.mydomain.newsapplication.api

import com.mydomain.newsapplication.model.NewsArticleList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("articlesearch.json")
    fun searchArticle(
        @Query("q") sf: String,
        @Query("page") page: Int,
        @Query ("api-key") key: String
    ): Call<NewsArticleList>
}