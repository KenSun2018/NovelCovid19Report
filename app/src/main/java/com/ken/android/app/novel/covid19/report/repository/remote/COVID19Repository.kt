package com.ken.android.app.novel.covid19.report.repository.remote


import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 保留參考用，無使用，改用 Rx
 * **/
class COVID19Repository (interceptor: Interceptor){
    companion object{
        private const val TAG = "COVID19Repository"
        private const val BASE_URL = "https://corona.lmao.ninja/v2/"

        const val TIME_OUT_CONNECT = 30
        const val TIME_OUT_READ = 30
        const val TIME_OUT_WRITE = 30
    }

    private var apiService : COVID19ApiService

    val isLoading = MutableLiveData<Boolean>(false)


    var globalTotalCaseLiveData = MutableLiveData<GlobalTotalCase>()
    var globalTotalCaseErrorLiveData = MutableLiveData<String>()



    var countriesLiveData = MutableLiveData<List<Country>>()
    var countriesErrorLiveData = MutableLiveData<String>()




    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(TIME_OUT_CONNECT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_READ.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_WRITE.toLong(), TimeUnit.SECONDS)
            .build()

        apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(COVID19ApiService::class.java)
    }


    fun loadGlobalTotalCase() {
        isLoading.value = true

        apiService.getGlobalTotalCase().enqueue(object : Callback<GlobalTotalCase>{
            override fun onFailure(call: Call<GlobalTotalCase>, t: Throwable) {
                isLoading.value = false
                globalTotalCaseErrorLiveData.value = "error : ${t.message}"

            }

            override fun onResponse(call: Call<GlobalTotalCase>, response: Response<GlobalTotalCase>) {
                isLoading.value = false
                if(response.isSuccessful){
                    globalTotalCaseLiveData.value = response.body()
                }else{
                    globalTotalCaseErrorLiveData.value = "response but not successful"
                }

            }

        })
    }

    fun loadCountries(){
        isLoading.value = true
        apiService.getCountries("deaths").enqueue(object : Callback<List<Country>>{
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                isLoading.value = false
                countriesErrorLiveData.value = t.message
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                isLoading.value = false
                if(response.isSuccessful){
                    countriesLiveData.value = response.body()
                }else{
                    countriesErrorLiveData.value = "response but not successful"
                }

            }

        })
    }

    fun loadCountriesBySorting(sort : String){
        isLoading.value = true
        apiService.getCountries(sort).enqueue(object : Callback<List<Country>>{
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                isLoading.value = false
                countriesErrorLiveData.value = t.message
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                isLoading.value = false
                if(response.isSuccessful){
                    countriesLiveData.value = response.body()
                }else{
                    countriesErrorLiveData.value = "response but not successful"
                }
            }
        })
    }

    fun search(country: String) {
        isLoading.value = true
        apiService.searchCountry(country).enqueue(object : Callback<Country>{
            override fun onFailure(call: Call<Country>, t: Throwable) {
                isLoading.value = true
                countriesErrorLiveData.value = t.message
            }

            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                isLoading.value = false

                if(response.isSuccessful){

                    val result = response.body()
                    if(result == null){
                        countriesErrorLiveData.value = "response is null"
                        return
                    }

                    val data = ArrayList<Country>()
                    data.add(result)
                    countriesLiveData.value = data

                }else{
                    countriesErrorLiveData.value = "response but not successful"
                }
            }

        })
    }


}