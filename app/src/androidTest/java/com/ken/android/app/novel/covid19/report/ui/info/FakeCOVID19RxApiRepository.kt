package com.ken.android.app.novel.covid19.report.ui.info

import androidx.test.InstrumentationRegistry.getContext
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.repository.bean.GlobalTotalCase
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.utils.Log
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.reflect.Type
import java.security.AccessController.getContext
import java.util.ArrayList

class FakeCOVID19RxApiRepository : COVID19RxApiRepository {
    companion object{
        private const val TAG = "FakeCOVID19Repo"
    }
    private val gson = Gson();

    override fun getGlobalTotalCase(): Single<GlobalTotalCase> {
        return Single.create(object : SingleOnSubscribe<GlobalTotalCase> {
            override fun subscribe(emitter: SingleEmitter<GlobalTotalCase>) {
                Log.i(TAG, "getGlobalTotalCase")
                val apiResponseString = readResFile("globalcase.json")
                Log.i(TAG, "getGlobalTotalCase apiResponseString = $apiResponseString")
                val apiResponse = gson.fromJson(apiResponseString, GlobalTotalCase::class.java)

                emitter.onSuccess(apiResponse)
            }
        })
    }

    override fun getCountries(): Single<List<Country>> {
        return Single.create { emitter ->
            Log.i(TAG, " getCountries() ")
            val apiResponseString = readResFile("countries.json")
            val mapType : Type = object : TypeToken<List<Country>>(){}.type
            emitter.onSuccess(gson.fromJson(apiResponseString, mapType))
        }
    }

    override fun getCountries(country: String): Single<List<Country>> {
        return Single.create { emitter ->
            Log.i(TAG, " getCountries($country) ")
            val apiResponseString = readResFile("countries.json")
            Log.i(TAG, " getCountries($country) apiResponseString = $apiResponseString")
            val mapType : Type = object : TypeToken<List<Country>>(){}.type
            emitter.onSuccess(gson.fromJson(apiResponseString, mapType))
        }
    }

    override fun searchCountry(country: String): Single<Country> {
        return Single.create(object : SingleOnSubscribe<Country>{
            override fun subscribe(emitter: SingleEmitter<Country>) {
                Log.i(TAG, " searchCountry($country) ")
                val apiResponseString = readResFile("countries.json")
                val mapType : Type = object : TypeToken<List<Country>>(){}.type
                val list : List<Country> = gson.fromJson(apiResponseString, mapType)
                //拿第一個做測試
                emitter.onSuccess(list[0])
            }
        })
    }

    fun readResFile(resourceName: String) : String{
        Log.i(TAG, " readResFile ($resourceName)")
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.context
        val classLoader = context.classLoader
        Log.i(TAG, " readResFile ($resourceName), classLoader = $classLoader")
        val inputStream = classLoader.getResourceAsStream(resourceName)
        Log.w(TAG, " readResFile ($resourceName), inputStream = $inputStream")
        return readInputStreamToString(inputStream)
    }

    private fun readInputStreamToString(inputStream: InputStream): String {
        val result = ByteArrayOutputStream()
        var read: Int = -1
        Log.w(TAG, " readInputStreamToString = $inputStream")
        inputStream.use { input ->
            result.use {
                while (input.read().also { read = it } != -1) {
                    it.write(read)
                }
            }
        }
        return result.toString("UTF-8")
    }

}