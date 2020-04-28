package com.ken.android.app.novel.covid19.report.ui.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.ken.android.app.novel.covid19.report.repository.bean.KiangGeoJson
import com.ken.android.app.novel.covid19.report.repository.remote.rx.TaiwanMaskRxApiRepository
import fake.Utils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FragmentTWMaskViewModelRxImplTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var twMaskViewModelRxImpl : FragmentTWMaskViewModelRxImpl

    @MockK
    private lateinit var mockRepository: TaiwanMaskRxApiRepository

    @MockK
    private lateinit var mockLoading : MutableLiveData<Boolean>

    private lateinit var expectGeoJson: KiangGeoJson

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        twMaskViewModelRxImpl = FragmentTWMaskViewModel.RxFactory(mockRepository).create(FragmentTWMaskViewModelRxImpl::class.java)
        twMaskViewModelRxImpl.setMockLoading(mockLoading)

        val gson = Gson();
        val expectFakeGeoJson = Utils.readResFile("geoJson.json")
        expectGeoJson = gson.fromJson<KiangGeoJson>(expectFakeGeoJson, KiangGeoJson::class.java)

        // if mockLoading is not false that mean developer is not set isLoading.value = false
        every { mockLoading.value } returns false
    }

    @Test
    fun test_viewModelLoadMaskMapData(){

        every { mockRepository.getPoint() } returns Single.just(expectGeoJson)
        twMaskViewModelRxImpl.loadMaskMapData()

        verifySequence {
            mockLoading.value = true // verify show progress bar
            mockRepository.getPoint() //verify call api
            mockLoading.value = false // verify hide progress bar
        }

        Assert.assertEquals(expectGeoJson, twMaskViewModelRxImpl.getGeoJsonLiveData().value)
    }
}