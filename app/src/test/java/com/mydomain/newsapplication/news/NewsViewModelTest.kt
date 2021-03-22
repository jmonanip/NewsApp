package com.mydomain.newsapplication.news

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mydomain.newsapplication.model.NewsArticleList
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito


@RunWith(JUnit4::class)
class NewsViewModelTest {
    private lateinit var testViewModel: NewsArticleViewModel

    @Mock
    val observer: Observer<NewsArticleList> = mock()

    var lifecycle: Lifecycle? = null

    @Before
    fun setup() {
        testViewModel = NewsArticleViewModel()
    }
    @Test
    fun initViewModel() {
        assertNotNull(testViewModel)
    }

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

}