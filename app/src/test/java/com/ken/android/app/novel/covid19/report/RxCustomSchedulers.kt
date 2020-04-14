package com.ken.android.app.novel.covid19.report

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


object RxCustomSchedulers{
    fun mainThread() : Scheduler{
        return Schedulers.trampoline()
    }
    fun io() : Scheduler {
        return Schedulers.io()
    }
}