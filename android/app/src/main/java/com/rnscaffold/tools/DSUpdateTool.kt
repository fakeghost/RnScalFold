package com.rnscaffold.tools

import android.util.Log
import com.google.gson.Gson
import com.rnscaffold.MainApplication
import com.rnscaffold.entity.JSSourceRequest
import com.rnscaffold.http.HttpManager
import com.rnscaffold.http.SchedulerTransfomer
import com.rnscaffold.http.UseSubScribes
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.lang.Exception
import java.util.*

class DSUpdateTool {
    private val configJSName = "";
    fun getUnzipReactParentBundle() {
        val path = MainApplication.getInstance().getExternalFilesDir("bundles")?.path + "/android"
        Log.i("热更解包出来的路径", path)
    }

//    fun needUpdate(newVerson: String) {
//        val path = MainApplication.getInstance().getExternalFilesDir("bundles")?.path + ""
//        val newConfig = File(path + "/running/" + configJSName);
//    }

//    fun unZipFromAssets(filePath, outputDirectory, requestCallBack) {
//        try {
//            dataSource = filePath
//        }
//    }

    fun getPackageInfo() {
        val jsSourceObj = JSSourceRequest()
        jsSourceObj.env = "release"
        jsSourceObj.modules = mutableListOf("main")
        jsSourceObj.name = "szbiz"
        jsSourceObj.nativeApiLevel = 10001L
        jsSourceObj.platform = "android"
//        JSSourceRequest.
        val jsonRequest = Gson().toJson(jsSourceObj)
        val httpManagerObject = HttpManager();
//        Log.i("看看参数", Gson().toJson(httpManagerObject))
        httpManagerObject.getHttpService()?.getDetail(toRequestBody(jsonRequest))?.compose(SchedulerTransfomer())
        ?.subscribe (
            UseSubScribes()
        )

    }

    private fun toRequestBody(value: String): RequestBody {
        return RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), value)
    }
}
