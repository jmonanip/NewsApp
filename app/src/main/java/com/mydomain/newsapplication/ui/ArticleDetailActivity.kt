package com.mydomain.newsapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.mydomain.newsapplication.R
import com.mydomain.newsapplication.model.NewsArticleDoc
import com.mydomain.newsapplication.util.Utils
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class ArticleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail)
        title = TITLE
        setBackButton()
        initialiseArticleDetail()
    }

    private fun setBackButton() {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialiseArticleDetail() {
        val gson = Gson()
        val articleDoc =
            gson.fromJson(intent.getStringExtra(ARTICLE_DETAIL), NewsArticleDoc::class.java)
        val imageView = findViewById<ImageView>(R.id.detail_image_view)

        if (!articleDoc.getMultimedia().isNullOrEmpty()) {
            val url = Utils.getImage(articleDoc.getMultimedia()!!)
            if (!url.isNullOrEmpty()) {
                Picasso.with(this)
                    .load(url)
                    .networkPolicy(
                        if (Utils.isNetworkConnectionAvailable(this)) NetworkPolicy.NO_CACHE else NetworkPolicy.OFFLINE
                    )
                    .into(imageView)
            }
        }

        val detail = findViewById<TextView>(R.id.article_detail)
        val sb = StringBuilder()
        sb.append(articleDoc.getAbstract()).append("\n\n").append(articleDoc.getLeadPara())
        if (!articleDoc.getWebUrl().isNullOrEmpty()) {
            sb.append("\n\n").append(articleDoc.getWebUrl())
        }
        detail.text = sb.toString()
        setupShare(articleDoc)
    }

    private fun setupShare(articleDoc: NewsArticleDoc) {
        val subject = getString(R.string.share_subject)
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = SHARE_TYPE
            putExtra(Intent.EXTRA_TEXT, articleDoc.getWebUrl())
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }, TITLE)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(share)
        }
    }

    companion object {
        const val TAG = "ArticleDetailActivity"
        const val ARTICLE_DETAIL = "article_detail"
        const val TITLE = "News Article"
        const val SHARE_TYPE = "text/plain"
    }
}
