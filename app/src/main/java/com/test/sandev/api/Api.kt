package com.test.sandev.api

import com.test.sandev.module.*
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

    @GET("v1/video/videolist")
    fun getVideoData() : Observable<BaseResponse<List<VideoModule>>>

    @GET("lg/coin/userinfo/json")
    fun getUserJiFen(@QueryMap map:Map<String,Int>) : Observable<BaseResponse<JifenModule>>

    @GET("v1/mine/url")
    fun getMineUrl() : Observable<BaseResponse<MineModule>>

    @GET("v1/home/update")
    fun getUpdate() : Observable<BaseResponse<UpdateModule>>


    @GET("v1/mine/isShow")
    fun getPack() : Observable<BaseResponse<PackModule>>

    @GET("v1/home/getSquare")
    fun getSqData() : Observable<BaseResponse<List<SqModule>>>

    @GET("v1/vv_home/getInfo")
    fun getAaData() : Observable<BaseResponse<HomeBean>>

    @GET("v1/vv_video/getV")
    fun getAvInfo() :Observable<BaseResponse<List<VideoModule>>>

    @GET("v1/home/vv_get")
    fun getVV() :Observable<BaseResponse<List<BannerBean>>>
}