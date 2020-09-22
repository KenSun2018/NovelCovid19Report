package com.ken.android.app.novel.covid19.report.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ken.android.app.novel.covid19.report.utils.Log
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
class DaggerViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory{

    companion object{
        private const val TAG = "DaggerViewModelFactory"
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.i(TAG, " create $modelClass")
        val creator = creators[modelClass]
            ?: creators.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}