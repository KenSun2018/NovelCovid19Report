package com.ken.android.app.novel.covid19.report.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.BuildConfig
import com.ken.android.app.novel.covid19.report.repository.bean.News
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.repository.remote.rx.NewsApiOrgRxApiRepository
import io.mockk.*

import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class NewsViewModelRxImplTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var newsViewModel : NewsViewModelRxImpl



    @MockK
    private lateinit var mockRepository: NewsApiOrgRxApiRepository

    @MockK
    private lateinit var mockLoading : MutableLiveData<Boolean>

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)

        //dummy
        newsViewModel = NewsViewModel.RxFactory(mockRepository).create(NewsViewModelRxImpl::class.java)
        newsViewModel.setMockLoading(mockLoading)

        // mock load finish get value always false, if not false then developer is not close loading
        // stub
        every { mockLoading.value } returns false
    }

    private fun createExpectNewsArticle() : NewsArticle{
        val expectNewsArticle = NewsArticle()
        expectNewsArticle.title = "COVID19 新聞 Title"
        expectNewsArticle.url = "https://news.google.com/topstories?hl=zh-TW&gl=TW&ceid=TW:zh-Hant"
        expectNewsArticle.description = "COVID19"
        return expectNewsArticle
    }

    @Test
    fun test_viewModeLoadCOVIDNews_responseSuccess(){
        val expectNews = News()
        expectNews.status = "ok"
        expectNews.totalResults = 1

        val expectNewsArticles = ArrayList<NewsArticle>()
        expectNewsArticles.add(createExpectNewsArticle())

        expectNews.articles = expectNewsArticles

        val language = Locale.getDefault().country



        every { mockRepository.getTopHeadline("covid", language, BuildConfig.NEWS_API_KEY) } returns Single.just(expectNews)



        newsViewModel.loadNews("covid")

        verifySequence {
            mockLoading.value = true
            mockRepository.getTopHeadline("covid", language, BuildConfig.NEWS_API_KEY)
            mockLoading.value = false
        }



        val realArticles = newsViewModel.getNewsLiveData().value

        Assert.assertNotNull(realArticles)
        Assert.assertEquals(expectNews.articles, newsViewModel.getNewsLiveData().value)
        Assert.assertEquals(false, newsViewModel.isLoading().value)
        val expectArticles = expectNews.articles


        for(i in expectArticles.indices){
            val expectArticle = expectArticles[i]
            val realArticle = realArticles!![i]
            Assert.assertEquals(expectArticle.title, realArticle.title)
            Assert.assertEquals(expectArticle.url, realArticle.url)
            Assert.assertEquals(expectArticle.description, realArticle.description)
        }

        Assert.assertEquals(false, newsViewModel.isLoading().value)
    }

    @Test
    fun test_viewModeLoadCOVIDNews_responseIOException(){

        val language = Locale.getDefault().country

        every { mockRepository.getTopHeadline("covid", language, BuildConfig.NEWS_API_KEY) } returns Single.error(IOException("403 Forbidden"))
        newsViewModel.loadNews("covid")


        verifySequence {
            mockLoading.value = true
            mockRepository.getTopHeadline("covid", language, BuildConfig.NEWS_API_KEY)
            mockLoading.value = false
        }
        Assert.assertEquals("403 Forbidden", newsViewModel.getErrorLiveData().value)
        Assert.assertEquals(false, newsViewModel.isLoading().value)
    }

}