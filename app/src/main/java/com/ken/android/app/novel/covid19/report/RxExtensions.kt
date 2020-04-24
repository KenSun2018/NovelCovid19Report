package com.ken.android.app.novel.covid19.report

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


operator fun CompositeDisposable.plusAssign(disposable: Disposable){
    add(disposable)
}