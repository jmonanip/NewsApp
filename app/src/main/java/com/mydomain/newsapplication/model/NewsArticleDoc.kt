package com.mydomain.newsapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsArticleDoc {
    @SerializedName("_id")
    @Expose
    private val id: String? = null

    @SerializedName("abstract")
    @Expose
    private val abstract: String? = null

    @SerializedName("web_url")
    @Expose
    private val webUrl: String? = null

    @SerializedName("snippet")
    @Expose
    private val snippet: String? = null

    @SerializedName("lead_paragraph")
    @Expose
    private val leadPara: String? = null

    @SerializedName("pub_date")
    @Expose
    private val pubDate: String? = null

    @SerializedName("word_count")
    @Expose
    private val wordCount: String? = null

    @SerializedName("multimedia")
    @Expose
    private var multimedia: List<ArticleMultimedia>? = null

    fun getId(): String? {
        return id;
    }

    fun getAbstract(): String? {
        return abstract
    }

    fun getWebUrl(): String? {
        return webUrl
    }

    fun getSnippet(): String? {
        return snippet
    }

    fun getDate(): String? {
        return pubDate
    }

    fun getWordCount(): String? {
        return wordCount
    }

    fun getMultimedia(): List<ArticleMultimedia>? {
        return multimedia
    }

    fun getLeadPara(): String? {
        return leadPara
    }
}

