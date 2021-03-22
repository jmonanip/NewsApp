package com.mydomain.newsapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsArticleList {

    @SerializedName ("status")
    @Expose
    var status: String? = null

    @SerializedName ("copyright")
    @Expose
    var copyright: String? = null

    @SerializedName ("response")
    @Expose
    var response: NewsArticleResponse? = null

    fun getNewsStatus(): String? {
        return status
    }

    fun getCopyWrite(): String? {
        return copyright
    }

    fun getNewsResponse(): NewsArticleResponse? {
        return response
    }
}