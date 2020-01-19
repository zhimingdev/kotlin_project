package com.test.sandev.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetWork {
    var client : OkHttpClient? = null
    companion object {
        var modk_url = "http://mock-api.com/mnEDaYgJ.mock/"
        var wan_url = "https://www.wanandroid.com/"

        var new_mock = "http://mock-api.com/NnQ6E1KY.mock/"
        var guang = "https://tenant.51yundong.me/"
    }

    fun getRetrofit(baseUrl: String): Retrofit {
        var logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        if (baseUrl.contains("wanandroid")) {
            client = OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(AddCookiesInterceptor())
                    //自定义拦截器用于日志输出
                    .build()
        }else{
            client = OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    //自定义拦截器用于日志输出
                    .build()
        }

        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //格式转换
                .client(client)
                .build()
        return retrofit
    }

    //可用于多种不同种类的请求
    fun <T> getApi(service: Class<T>): T {
        return getRetrofit(modk_url).create(service)
    }

    fun <T> getWanApi(service:Class<T>):T{
        return getRetrofit(wan_url).create(service)
    }

    fun <T> getNewMockApi(service:Class<T>):T{
        return getRetrofit(new_mock).create(service)
    }

    fun <T> getGuanApi(service:Class<T>):T{
        return getRetrofit(guang).create(service)
    }

}