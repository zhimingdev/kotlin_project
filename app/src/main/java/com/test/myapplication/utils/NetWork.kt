package com.test.myapplication.utils

import android.app.PendingIntent.getService
import com.test.myapplication.api.Api
import com.test.myapplication.utils.NetWork.Companion.wan_url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetWork {
    companion object {
        var modk_url = "http://mock-api.com/mnEDaYgJ.mock/"
        var wan_url = "http://www.wanandroid.com/"
    }

    fun getRetrofit(baseUrl: String): Retrofit {
        var logInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        var clien = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                //自定义拦截器用于日志输出
                .build()

        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //格式转换
                .client(clien)
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
}