package com.test.sandev.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.ToastUtils
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

import java.util.concurrent.TimeUnit

object RxUtil {

    val INTERVAL: Long = 60

    /**
     * 实现IO线程与主线程之间的切换
     */
    fun switchThread(ioListener: OnEventListener, mainListener: OnEventListener) {
        Observable.create(ObservableOnSubscribe<Any> { emitter ->
            var o = ioListener.onEvent(null)
            emitter.onNext(o)
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { o -> mainListener.onEvent(o) }
    }

    /**
     * 1秒内防重点击
     *
     * @param view
     * @param listener
     */
    @JvmOverloads
    fun singleClick(view: View, listener: OnEventListener? = null) {
        RxView.clicks(view)
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (listener != null) {
                    try {
                        listener.onEvent(null)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.i("RxUtil", "requestCode onEvent error:" + e.message)
                    }

                }
            }
    }

    fun requestCode(textView: TextView) {
        requestCode(textView, INTERVAL, null, null)
    }


    fun requestCode(textView: TextView, onDataListener: OnDataListener, listener: OnEventListener) {
        requestCode(textView, INTERVAL, onDataListener, listener)
    }

    /**
     * 请求验证码
     *
     * @param textView
     * @param interval
     * @param listener
     */
    @SuppressLint("CheckResult")
    fun requestCode(textView: TextView, interval: Long, onDataListener: OnDataListener?, listener: OnEventListener?) {
        RxView.clicks(textView)
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                if (!ObjectUtils.isEmpty(onDataListener)) {
                    if (!onDataListener!!.checkData()) {
                        return@Consumer
                    }
                }

                Observable.interval(0, 1, TimeUnit.SECONDS, Schedulers.io())
                    .take(interval, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Long> {
                        override fun onSubscribe(d: Disposable) {
                            textView.isEnabled = false
                            textView.setTextColor(Color.parseColor("#FFCCCCCC"))
                            if (listener != null) {
                                try {
                                    listener.onEvent(null)
                                    ToastUtils.showShort("验证码发送中")
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    Log.i("RxUtil", "requestCode onEvent error:" + e.message)
                                }

                            }
                        }

                        override fun onNext(time: Long) {
                            textView.text = (interval - time).toString() + "秒后重新获取"
                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onComplete() {
                            textView.isEnabled = true
                            textView.setTextColor(Color.parseColor("#FF4699FA"))
                            textView.text = "重新获取"
                        }
                    })
            })
    }


    fun textChange(textView: TextView, listener: OnEventListener) {
        textChange(textView, 1000, listener)
    }

    /**
     * 内容改变（过滤小与timeOut间隔的改变）
     *
     * @param textView
     * @param timeOut
     * @param listener
     */
    @SuppressLint("CheckResult")
    fun textChange(textView: TextView, timeOut: Long, listener: OnEventListener?) {
        RxTextView.afterTextChangeEvents(textView)
            .subscribeOn(Schedulers.io())
            .debounce(timeOut, TimeUnit.MILLISECONDS)
            .filter { true }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                if (listener != null) {
                    try {
                        listener.onEvent(event.editable()!!.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.i("RxUtil", "requestCode onEvent error:" + e.message)
                    }

                }
            }
    }

    interface OnDataListener {
        fun checkData(): Boolean
    }

    interface OnEventListener {
        fun onEvent(event: Any?)
    }
}
