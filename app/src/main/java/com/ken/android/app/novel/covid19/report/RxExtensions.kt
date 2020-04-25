package com.ken.android.app.novel.covid19.report

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


fun Disposable.addTo(compositeDisposable: CompositeDisposable){
    this.also { compositeDisposable.add(it) }
}