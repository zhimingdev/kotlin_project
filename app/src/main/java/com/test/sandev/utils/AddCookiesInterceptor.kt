package com.test.sandev.utils

import com.blankj.utilcode.util.SPUtils

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var newBuilder = request.newBuilder()
        var response = chain.proceed(request)
        val requestUrl = request.url().toString()
        if (response.headers() != null && (requestUrl.contains("login") || requestUrl.contains("register") || requestUrl.contains("userinfo"))) {
            if (SPUtils.getInstance().getString("cookie").isNotBlank()) {
                newBuilder.addHeader("Cookie",SPUtils.getInstance().getString("cookie"))
                response = chain.proceed(newBuilder.build())
            }else{
                var header = response.headers("set-cookie")
                SPUtils.getInstance().put("cookie",encodeCookie(header))
            }
        }
        return response
    }

    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
                .map { cookie ->
                    cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                .forEach {
                    it.filterNot { set.contains(it) }.forEach { set.add(it) }
                }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }
}
