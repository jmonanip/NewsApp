package com.mydomain.newsapplication.util

import com.mydomain.newsapplication.model.ArticleMultimedia
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Utils uni test
 */
@RunWith(RobolectricTestRunner::class)
class UtilsTest {
    @Test
    fun testInvalidPrice() {
        assertTrue(Utils.getImage(ArrayList()).isEmpty())

        val mediaList = ArrayList<ArticleMultimedia>()
        assertTrue(Utils.getImage(ArrayList()).isEmpty())

        val testUrl = "http://test.image"
        val testMedia = ArticleMultimedia()
        testMedia.setUrl(testUrl)
        mediaList.add(testMedia)

        val testUrl2 = "http://test2.image"
        val media2 = ArticleMultimedia()
        media2.setUrl(testUrl2)
        mediaList.add(media2)

        assertTrue(Utils.getImage(mediaList).contains(testUrl))

        mediaList[0] = media2
        assertTrue(Utils.getImage(mediaList).contains(testUrl2))
    }

    @Test
    fun isNetworkConnectionAvailable() {
        assertTrue(Utils.isNetworkConnectionAvailable(RuntimeEnvironment.systemContext))
    }
}
