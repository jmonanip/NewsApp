package com.mydomain.newsapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mydomain.newsapplication.R
import com.mydomain.newsapplication.model.ArticleMultimedia
import com.mydomain.newsapplication.model.NewsArticleDoc
import com.mydomain.newsapplication.util.Utils
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.util.*

class ArticleAdapter(onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private val articleList: MutableList<NewsArticleDoc> = ArrayList()
    private var itemClickListener: OnItemClickListener = onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ArticleViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(articleViewHolder: ArticleViewHolder, i: Int) {
        val article: NewsArticleDoc = articleList[i]
        articleViewHolder.bind(article, itemClickListener)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    fun addItems(newItems: List<NewsArticleDoc>?) {
        articleList.addAll(newItems!!)
        notifyDataSetChanged()
    }

    fun clearList() {
        articleList.clear()
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun onItemClicked(article: NewsArticleDoc)
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById<TextView>(R.id.title)
        private var imageView = itemView.findViewById<ImageView>(R.id.image_view)

        fun bind(article: NewsArticleDoc, clickListener: OnItemClickListener) {
            title.text = article.getAbstract() ?: article.getSnippet() ?: ""
            if (!article.getMultimedia().isNullOrEmpty()) {
                updateImage(Collections.synchronizedList(article.getMultimedia()))
            }
            itemView.setOnClickListener { clickListener.onItemClicked(article) }
        }

        private fun updateImage(media: MutableList<ArticleMultimedia>) {
            var url = Utils.getImage(media)
            if (!url.isNullOrEmpty()) {
                Picasso.with(itemView.context)
                    .load(url)
                    .networkPolicy(
                        if (Utils.isNetworkConnectionAvailable(itemView.context)) NetworkPolicy.NO_CACHE else NetworkPolicy.OFFLINE
                    )
                    .into(imageView)
            }
        }
    }

    companion object {
        const val TAG = "ArticleAdapter"
    }
}