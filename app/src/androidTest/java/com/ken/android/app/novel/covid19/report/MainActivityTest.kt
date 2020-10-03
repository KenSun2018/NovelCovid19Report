package com.ken.android.app.novel.covid19.report

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.ken.android.app.novel.covid19.report.ui.news.daggerviewholder.DaggerNewsArticleViewHolder
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    //宣告要測試的Activity，請Runner執行Test區塊前直接開啟
    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup(){
        val myApplication : MyApplication = activityTestRule.activity.application as MyApplication
        myApplication.setTestAppComponent(DaggerInstrumentedTestAppComponent.builder().build())

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ken.android.app.novel.covid19.report", appContext.packageName)
    }


    @Test
    fun testMainActivity(){


        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.main_tablayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //測試有讀到 假資料
        Espresso.onView(ViewMatchers.withText("1201933"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("城鎮測試2"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("相關新聞"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.global_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DaggerNewsArticleViewHolder>(0,
                    ViewActions.click()
                ))
        Intents.release()
    }
}