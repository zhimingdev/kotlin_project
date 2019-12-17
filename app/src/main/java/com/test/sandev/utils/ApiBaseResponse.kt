package com.test.sandev.utils

import android.accounts.NetworkErrorException
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.test.sandev.api.Api
import com.test.sandev.module.ApiError
import com.test.sandev.module.BaseResponse
import java.net.ConnectException
import java.net.SocketTimeoutException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class ApiBaseResponse<T>(private val activity: FragmentActivity) : Observer<BaseResponse<T>> {
    internal lateinit var error: ApiError

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: BaseResponse<T>) {
        when {
            t.code == 200 -> onSuccess(t.data)
            t.errorCode == 0 -> onSuccess(t.data)
            else -> onCodeError(t)
        }
    }

    override fun onError(e: Throwable) {
        if (e is ConnectException ||
                e is NetworkErrorException ||
                e is SocketTimeoutException) {
            error = ApiError()
            error.code = 404
            error.msg = "网络错误"
        } else {
            error = ApiError()
            error.code = 400
            error.msg = "参数错误"
        }
        onFail(error)
    }

    override fun onComplete() {}

    abstract fun onSuccess(t: T?)

    //请求成功但返回的code码不是200的回调方法,这里抽象方法申明
    abstract fun onCodeError(tBaseReponse: BaseResponse<*>)

    abstract fun onFail(e: ApiError)
}
