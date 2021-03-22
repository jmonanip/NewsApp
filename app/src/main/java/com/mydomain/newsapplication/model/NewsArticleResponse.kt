package com.mydomain.newsapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsArticleResponse {
    @SerializedName ("docs")
    @Expose
    var docs: List<NewsArticleDoc>? = null
    
    fun getArticleDocs(): List<NewsArticleDoc>? {
        return docs
    }
}