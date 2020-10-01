package com.ken.android.app.novel.covid19.report.repository.remote.rx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.ken.android.app.novel.covid19.report.BuildConfig
import com.ken.android.app.novel.covid19.report.repository.bean.News
import fake.Utils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.BufferedSource
import okio.buffer
import okio.source
import org.junit.*

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class NewsApiOrgRxApiRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var newsApiOrgRxApiRepository: NewsApiOrgRxApiRepository
    private lateinit var mockWebServer : MockWebServer

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        val newsApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(NewsApiOrgRxApiService::class.java)
        newsApiOrgRxApiRepository = NewsApiOrgRxApiRepository(newsApiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_getTopHeadlines_responseSuccess(){

        enqueueResponse("topHeadlines.json")

        val expectApiResponseString = Utils.readResFile("topHeadlines.json")
        val language = Locale.getDefault().country
        val news = newsApiOrgRxApiRepository.getTopHeadline("covid", language, BuildConfig.NEWS_API_KEY).blockingGet()

        val gson = Gson()
        val expectResponse = gson.fromJson(expectApiResponseString, News::class.java)


        val realArticles = news.articles
        val expectArticles = expectResponse.articles

        for(i in realArticles.indices){
            val realArticle = realArticles[i]
            val expectArticle = expectArticles[i]
            Assert.assertEquals(expectArticle.author, realArticle.author)
            Assert.assertEquals(expectArticle.content, realArticle.content)
            Assert.assertEquals(expectArticle.description, realArticle.description)
            Assert.assertEquals(expectArticle.publishedAt, realArticle.publishedAt)

            Assert.assertEquals(expectArticle.title, realArticle.title)
            Assert.assertEquals(expectArticle.url, realArticle.url)
            Assert.assertEquals(expectArticle.urlToImage, realArticle.urlToImage)
        }
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source: BufferedSource = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}