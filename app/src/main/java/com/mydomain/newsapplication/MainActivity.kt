package com.mydomain.newsapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.gson.Gson
import com.mydomain.newsapplication.model.NewsArticleDoc
import com.mydomain.newsapplication.news.NewsArticleFragment
import com.mydomain.newsapplication.news.NewsArticleViewModel
import com.mydomain.newsapplication.ui.ArticleDetailActivity

class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var newsArticleViewModel: NewsArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView = findViewById(R.id.search_view)
        newsArticleViewModel = ViewModelProvider(this).get(NewsArticleViewModel::class.java)
        initializeLookupScreen(savedInstanceState)
        initializeSearchView()
    }

    private fun initializeLookupScreen(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            return
        }

        val lookupAcronymFragment = NewsArticleFragment().newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, lookupAcronymFragment)
                .commit()
        title = TITLE
    }

    private fun initializeSearchView() {
        searchView.queryHint = getString(R.string.search_article_hint)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    newsArticleViewModel.setQuery(query.trim())
                    searchView.clearFocus()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    newsArticleViewModel.clearSearchList()
                }
                return false
            }
        })
        searchView.setOnCloseListener {
            newsArticleViewModel.clearSearchList()
            false
        }

        newsArticleViewModel.getQuery().observe(this) {
            if (!it.isNullOrEmpty()) {
                newsArticleViewModel.fetchArticleList()
            }
        }

        newsArticleViewModel.getErrorResponse().observe(this) {
            if (!it.isNullOrEmpty()) {
                Log.e(TAG, "Error: $it")
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showArticle(articleDoc: NewsArticleDoc) {
        intent = Intent(this, ArticleDetailActivity::class.java)
        val gson = Gson()
        intent.putExtra(ArticleDetailActivity.ARTICLE_DETAIL, gson.toJson(articleDoc))
        startActivity(intent)
    }

    companion object {
        const val TAG = "MainActivity"
        const val TITLE = "N Y Times"
    }
}