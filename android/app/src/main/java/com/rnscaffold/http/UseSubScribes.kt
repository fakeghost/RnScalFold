package com.rnscaffold.http

import android.util.Log
import com.google.gson.Gson
import com.rnscaffold.entity.JSSourceEntity

class UseSubScribes : BaseSubscriber<BaseResponse<List<JSSourceEntity>>>() {
    override fun onDoNext(result: BaseResponse<List<JSSourceEntity>>) {
        val gson = Gson()
        val result = gson.toJson(result.data)
        Log.i("测试测试我得数据", result)
//        Log.i("返回的结果", result)
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        Log.i("看看错误", e.toString())
    }
}