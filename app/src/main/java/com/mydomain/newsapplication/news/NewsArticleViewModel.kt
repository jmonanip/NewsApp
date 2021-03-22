package com.mydomain.newsapplication.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mydomain.newsapplication.api.NewsApiInterface
import com.mydomain.newsapplication.api.NewsServiceClient
import com.mydomain.newsapplication.model.NewsArticleList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsArticleViewModel : ViewModel() {
    private val articleList: MutableLiveData<NewsArticleList> = MutableLiveData()
    private val currentSearchPage: MutableLiveData<Int> = MutableLiveData()
    private val queryLiveData: MutableLiveData<String> = MutableLiveData()
    private val errorResponse = SingleLiveEvent<String?>()

    fun getQuery(): MutableLiveData<String> {
        return queryLiveData
    }

    fun setQuery(query: String) {
        queryLiveData.postValue(query)
    }

    fun getErrorResponse(): SingleLiveEvent<String?> {
        return errorResponse
    }

    fun searchNextPage() {
        val page = currentSearchPage.value?.toInt() ?: 1
        currentSearchPage.postValue(page.plus(1))
    }

    fun getSearchPageLiveData(): MutableLiveData<Int> {
        return currentSearchPage
    }

    fun clearSearchList() {
        queryLiveData.value = null
        errorResponse.value = null
        articleList.postValue(NewsArticleList())
        currentSearchPage.value = null
    }

    fun getArticleList(): MutableLiveData<NewsArticleList> {
        return articleList
    }

    fun fetchArticleList() {
        if (queryLiveData.value.isNullOrEmpty()) {
            return
        }
        if (NewsServiceClient.client == null) {
            return
        }

        val apiService: NewsApiInterface =
            NewsServiceClient.client!!.create(NewsApiInterface::class.java)
        var page = currentSearchPage.value ?: 1
        if (page <= 0) {
            page = page.plus(1)
        }

        val call: Call<NewsArticleList> = apiService.searchArticle(queryLiveData.value!!, page, API_KEY)
        call.enqueue(object : Callback<NewsArticleList> {
            override fun onResponse(
                call: Call<NewsArticleList>,
                response: Response<NewsArticleList>
            ) {
                if (response.isSuccessful) {
                    val searchList: NewsArticleList? = response.body()
                    articleList.postValue(searchList)
                } else {
                    Log.e(TAG, "fetchArticleList: Response Code: " + response.code())
                    errorResponse.postValue("Server Error: " + response.code())
                }
            }

            override fun onFailure(call: Call<NewsArticleList>, t: Throwable?) {
                Log.e(TAG, "fetchArticleList: Response Code: ", t)
                errorResponse.postValue("Server Exception: " + t?.message)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        articleList.value = null
        currentSearchPage.value = null
        errorResponse.value = null
    }

    companion object {
        const val TAG: String = "NewsArticleViewModel"
        const val API_KEY = "OKsEwghCzAPR3kRr7Hp51cFn2tMfXWgj"
    }
}
