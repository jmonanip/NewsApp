package com.mydomain.newsapplication.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.mydomain.newsapplication.model.ArticleMultimedia

object Utils {
    const val IMAGE_URL = "https://www.nytimes.com/"

    fun isNetworkConnectionAvailable(context: Context): Boolean {
        var isNetworkConnectionAvailable = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null) {
            isNetworkConnectionAvailable =
                activeNetworkInfo.state == NetworkInfo.State.CONNECTED
        }
        return isNetworkConnectionAvailable
    }

    fun getImage(multimedia: List<ArticleMultimedia>): String {
        for (media in multimedia) {
            if (!media.getPhotoUrl().isNullOrEmpty()) {
                return IMAGE_URL + media.getPhotoUrl()
            }
        }
        return ""
    }
}
