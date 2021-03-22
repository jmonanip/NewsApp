package com.mydomain.newsapplication.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mydomain.newsapplication.MainActivity
import com.mydomain.newsapplication.R
import com.mydomain.newsapplication.model.NewsArticleDoc
import com.mydomain.newsapplication.model.NewsArticleList
import com.mydomain.newsapplication.ui.ArticleAdapter

class NewsArticleFragment : ArticleAdapter.OnItemClickListener, Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArticleViewModel: NewsArticleViewModel
    private var articleAdapter: ArticleAdapter? = null

    fun newInstance(): NewsArticleFragment {
        return NewsArticleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.news_search_fragment, container, false)
        initializeView(view)
        return view
    }

    private fun initializeView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        articleAdapter = ArticleAdapter(this)
        recyclerView.adapter = articleAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsArticleViewModel =
            ViewModelProvider(requireActivity()).get(NewsArticleViewModel::class.java)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    newsArticleViewModel.searchNextPage()
                }
            }
        })
        setUpObservers()
    }

    private fun setUpObservers() {
        val searchListObserver =
            Observer<NewsArticleList?> { searchList ->
                if (searchList?.response == null
                    || searchList.response!!.docs.isNullOrEmpty()
                ) {
                    (recyclerView.adapter as ArticleAdapter).clearList()
                    searchList?.let {
                        if (searchList.response?.docs?.isEmpty() == true)
                            Toast.makeText(
                                activity,
                                "No matching results or empty list",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                } else {
                    (recyclerView.adapter as ArticleAdapter)
                        .addItems(searchList.response?.docs)
                }
            }
        newsArticleViewModel.getArticleList()
            .observe(viewLifecycleOwner, searchListObserver)

        newsArticleViewModel.getSearchPageLiveData().observe(viewLifecycleOwner,
            Observer {
                newsArticleViewModel.fetchArticleList()
            })

        newsArticleViewModel.getErrorResponse().observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                Log.e(TAG, "Error: $it")
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClicked(article: NewsArticleDoc) {
        val activity = activity as MainActivity
        activity.showArticle(article)
    }

    companion object {
        private const val TAG = "NewsArticleFragment"
    }
}