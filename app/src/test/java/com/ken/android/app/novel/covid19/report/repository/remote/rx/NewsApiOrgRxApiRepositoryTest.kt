package com.ken.android.app.novel.covid19.report.repository.remote.rx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.ken.android.app.novel.covid19.report.BuildConfig
import com.ken.android.app.novel.covid19.report.repository.bean.News
import com.ken.android.app.novel.covid19.report.ui.news.NewsViewModelRxImpl
import fake.Utils
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.*

/**
 * 此測試可有可無，僅用來模擬 server team api 尚未實作，
 * 但已協調好 json 格式時，可以先放在自定義 folder ，
 * client 先接 retrofit 不用等 server
 * **/
class NewsApiOrgRxApiRepositoryTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private fun getFakeRepositoryFromFileName(fileName : String) : NewsApiOrgRxApiRepository{
        return NewsApiOrgRxApiRepository(Utils.getFakeResponseInterceptFromFile(fileName))
    }



    @Test
    fun test_getTopHeadlines_responseSuccess(){
        val repository = getFakeRepositoryFromFileName("topHeadlines.json")
        val expectApiResponseString = Utils.readResFile("topHeadlines.json")
        val language = Locale.getDefault().country
        val news = repository.getTopHeadline("covid", language, BuildConfig.NEWS_API_KEY).blockingGet()

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

}