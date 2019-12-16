package com.test.myapplication.api

import com.test.myapplication.module.BannerBean
import com.test.myapplication.module.HomeBean
import com.test.myapplication.module.BaseResponse
import com.test.myapplication.module.UserModule
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("woshishui")
    fun getData(@QueryMap map:Map<String,String> ):Call<Void>

    @GET("v1/home/getBanner")
    fun getBanner():Observable<BaseResponse<List<BannerBean>>>

    @GET("v1/home/getData")
    fun getHomeData():Observable<BaseResponse<HomeBean>>

    @FormUrlEncoded
    @POST("user/register")
    fun getUserInfo(@FieldMap map:Map<String,String>) : Observable<BaseResponse<UserModule>>

    @FormUrlEncoded
    @POST("user/login")
    fun getUserLogin(@FieldMap map:Map<String,String>) : Observable<BaseResponse<UserModule>>
}