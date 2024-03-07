package com.rnscaffold.http

import android.util.Log
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rnscaffold.MainApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// 单例类
class HttpManager {
    private val hostProduction = "xxx"
    private val hostDev = "xxx"
    private val hostUat = "xxx"
    private val timeout: Long = 50
    private var BASEURL = hostDev
    private var httpService: HttpService? = null

    init {
        val packageName = MainApplication.getInstance().packageName

//        if(packageName.endsWith("UAT")) {
//            BASE_URL = host_uat
//        } else
        BASEURL = when {
            packageName.endsWith("UAT") -> hostUat
            packageName.endsWith("TEST") -> hostDev
            else -> hostProduction
        }

        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MainApplication.getInstance()))

        val client = OkHttpClient().newBuilder().cookieJar(cookieJar).addInterceptor { chain ->
            val token = Math.abs("focusin.rn".hashCode() * 2 + 1008611) + System.currentTimeMillis()
            val request = chain.request().newBuilder().addHeader("AuthToken", "$token").build()
            chain.proceed(request)
        }
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build()

//        Log.i("看看client", Gson().toJson(client))


//        val gson = GsonBuilder().setLenient().create()

        httpService = Retrofit.Builder()
                .client(client)
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(HttpService::class.java)
    }


    fun getHttpService(): HttpService? {
        return httpService
    }
}

