package com.test.myapplication.utils

import com.blankj.utilcode.util.SPUtils

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        if (response.headers() != null && (requestUrl.contains("login") || requestUrl.contains("register"))) {
            var header = response.header("set-cookie")
            SPUtils.getInstance().put("cookie",header)
        }
        return response
    }
}
