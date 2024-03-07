package com.rnscaffold.http

import com.rnscaffold.entity.JSSourceEntity
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface HttpService {
    @POST("/api/basic/user/rn/detail")
    fun getDetail(@Body array: RequestBody): Observable<BaseResponse<List<JSSourceEntity>>>
}