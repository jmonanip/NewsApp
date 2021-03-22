package com.mydomain.newsapplication.model

import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticleMultimedia {
    @SerializedName("url")
    @Expose
    private var imgUrl: String? = null

    fun getPhotoUrl(): String? {
        return imgUrl
    }

    @VisibleForTesting
    fun setUrl(imgUrl: String) {
        this.imgUrl = imgUrl
    }
}