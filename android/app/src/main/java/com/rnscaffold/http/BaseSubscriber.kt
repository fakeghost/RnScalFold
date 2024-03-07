package com.rnscaffold.http

import android.util.Log
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseSubscriber<T> : Observer<T> {
    override fun onNext(t: T) {
//        if(t !== null && t is BaseResponse<*>) {
//            Log.d("BaseSubscriber", t.message)
//        }

        onDoNext(t)

    }

    abstract fun onDoNext(result: T)

    override fun onSubscribe(d: Disposable) {

    }

    override fun onError(e: Throwable) {
        Log.d("错误回调", "进入这里看看")
    }

    override fun onComplete() {
        Log.d("完成回调", "进入这里看看")
    }
}