package com.ken.android.app.novel.covid19.report.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ken.android.app.novel.covid19.report.utils.Log
import io.reactivex.disposables.CompositeDisposable

open class BaseRxViewModel : ViewModel() {
    companion object{
        const val TAG = "BaseRxViewModel";
    }
    protected val compositeDisposable = CompositeDisposable()
    protected var isLoading = MutableLiveData<Boolean>(false)

    @VisibleForTesting
    fun setMockLoading(isLoading: MutableLiveData<Boolean>){
        this.isLoading = isLoading
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, " onCleared ")
        compositeDisposable.dispose()
    }
}