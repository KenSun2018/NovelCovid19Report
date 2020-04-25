package com.ken.android.app.novel.covid19.report.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseRxViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    protected var isLoading = MutableLiveData<Boolean>(false)

    @VisibleForTesting
    fun setMockLoading(isLoading: MutableLiveData<Boolean>){
        this.isLoading = isLoading
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}