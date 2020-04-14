package com.ken.android.app.novel.covid19.report

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// for unit test
object RxCustomSchedulers {
    fun mainThread() : Scheduler{
        return AndroidSchedulers.mainThread()
    }
    fun io() : Scheduler{
        return Schedulers.io()
    }
}